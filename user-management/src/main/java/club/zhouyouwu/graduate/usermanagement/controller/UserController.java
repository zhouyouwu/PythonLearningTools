package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.constant.CodeMsg;
import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.entity.UserInfo;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import club.zhouyouwu.graduate.usermanagement.utils.CipherUtil;
import club.zhouyouwu.graduate.usermanagement.utils.ImageUtil;
import club.zhouyouwu.graduate.usermanagement.utils.SnowFlake;
import club.zhouyouwu.graduate.usermanagement.vo.UserInfoVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Base64;
import java.util.HashMap;

/**
 * 登录流程
 * B向S请求公钥，B用得到的公钥向S发送注册信息|登录信息
 * 登录成功后S生成token，用用户明文密钥解锁用户信息同token一起发送RSA，
 * 私钥废弃，B收到token、用户信息存储；
 * 注册成功后直接登录，用用户明文加密用户信息存储AES，返回token及用户信息私钥废弃同上。
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    /**
     * 登录注册时获取公钥
     *
     * @param userId 不存在则-1
     * @return 用户id+公钥
     * @throws Exception
     */
    @GetMapping("/{userId}/publicKey")//todo 没必要暂不删除，不再使用
    public Result getPublicKey(@PathVariable long userId) throws Exception {
        if (ConstantInfo.USER_ID_NOT_EXIST == userId) {
            //临时id注册或登录以后才固定下来
            userId = SnowFlake.getInstance(ConstantInfo.WORKER_ID, ConstantInfo.DATACENTER_ID).nextId();
        }

        KeyPair keyPair = new CipherUtil.RSA().getKeyPair();
        String privateKey = Base64.getUrlEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getUrlEncoder().encodeToString(keyPair.getPublic().getEncoded());
        redisTemplate.boundValueOps(Long.toString(userId)).append(privateKey);

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", Long.toString(userId));
        map.put("publicKey", publicKey);
        return Result.ok(map);
    }

    //创建了token所以post
    @PostMapping("/{userId}/token")
    public Result login(@PathVariable long userId, @RequestParam String password) throws Exception {

        if (userService.loginCheck(userId, password)) {
            User user = userService.getUserInfo(userId, password);

            return Result.ok(user);
        }

        Result result = new Result();
        result.setCode(CodeMsg.PASSWORD_ERROR.getCode());
        result.setMsg(CodeMsg.PASSWORD_ERROR.getMsg());
        return result;
    }

    @PostMapping("/{userId}")//todo 区分权限
    public Result register(@PathVariable long userId, @RequestParam String userVoString) throws Exception {
        String privateKey = redisTemplate.boundValueOps(Long.toString(userId)).get();

        CipherUtil.RSA rsa = new CipherUtil.RSA();
        userVoString = rsa.decrypt(privateKey, userVoString);

        UserInfoVo userVo = new Gson().fromJson(userVoString, UserInfoVo.class);
        UserInfo info = new UserInfo();
        info.setPhoneNo(userVo.getPhoneNo());
        info.setNickname(userVo.getNickname());
        info.setProfilePhoto(userVo.getProfilePhoto());
        info.setEMail(userVo.getEMail());

        User user = new User();
        user.setUserId(userId);
        user.setUserInfo(info);

        String salt = BCrypt.gensalt(ConstantInfo.LOG_ROUNDS);
        String password = BCrypt.hashpw(userVo.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);

        userService.createUser(user);
        return Result.ok("注册成功！");
    }

    @PutMapping("/{userId}")
    public Result updateInfo(@PathVariable long userId, @RequestBody UserInfo userinfo){

        userService.updateUserInfo(userId, userinfo);
        return Result.ok("更新成功");
    }

    @GetMapping("/{userId}")
    public Result getUserInfo(@PathVariable long userId){

        return Result.ok(userService.getUserInfo(userId, "Detail"));
    }

    @DeleteMapping("/{userId}")
    public Result delUser(@PathVariable long userId){
        userService.delUser(userId);

        return Result.ok("删除成功");
    }

    @PostMapping("/{userId}/photo")
    public Result updatePhoto(@PathVariable long userId, MultipartFile file) throws IOException {

        String fileName = SnowFlake.getInstance(ConstantInfo.WORKER_ID,ConstantInfo.DATACENTER_ID_FOR_IMAGE).nextId()+".jpg";
        ImageUtil.storeImage(file, fileName);

        userService.updateProfilePhoto(userId, fileName);
        return Result.ok("头像更换成功");
    }
}
