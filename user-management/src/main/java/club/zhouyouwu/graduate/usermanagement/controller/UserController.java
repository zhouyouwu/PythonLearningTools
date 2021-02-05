package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.constant.CodeMsg;
import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.entity.UserInfo;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import club.zhouyouwu.graduate.usermanagement.utils.CipherUtil;
import club.zhouyouwu.graduate.usermanagement.utils.SnowFlake;
import club.zhouyouwu.graduate.usermanagement.vo.UserInfoVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.util.Base64;
import java.util.Date;
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
    private UserService service;

    /**
     * 登录注册时获取公钥
     *
     * @param userId 不存在则-1
     * @return 用户id+公钥
     * @throws Exception
     */
    @GetMapping("/{userId}/publicKey")
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

    @PostMapping("/{userId}/token")
    public Result login(@PathVariable long userId, String password) throws Exception {
        String privateKey = redisTemplate.boundValueOps(Long.toString(userId)).get();

        CipherUtil.RSA rsa = new CipherUtil.RSA();
        String dePassword = rsa.decrypt(privateKey, password);

        String token;
        if (service.loginCheck(userId, dePassword)) {
            String text = userId + "&" + (new Date().getTime() + 1000 * 60 * 30);
            CipherUtil.AES aes = new CipherUtil.AES();

            token = aes.encrypt(CipherUtil.DEFAULT_KEY, text);
            User user = service.getUserInfo(userId, password);

            HashMap<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("token", token);

            return Result.ok(map);
        }

        Result result = new Result();
        result.setCode(CodeMsg.PASSWORD_ERROR.getCode());
        result.setMsg(CodeMsg.PASSWORD_ERROR.getMsg());
        return result;
    }

    @PostMapping("/{userId}")//todo 区分权限
    public Result register(@PathVariable long userId, String userVoString) throws Exception {
        String privateKey = redisTemplate.boundValueOps(Long.toString(userId)).get();

        CipherUtil.RSA rsa = new CipherUtil.RSA();
        userVoString = rsa.decrypt(privateKey, userVoString);

        UserInfoVo userVo = new Gson().fromJson(userVoString, UserInfoVo.class);
        UserInfo info = new UserInfo();
        info.setPhoneNo(userVo.getPhoneNo());

        CipherUtil.AES aes = new CipherUtil.AES();
        String plaintext = aes.encrypt(userVo.getPassword(), new Gson().toJson(info));
        String ciphertext = aes.encrypt(userVo.getPassword(), plaintext);

        User user = new User();
        user.setUserId(userId);
        user.setNickname(userVo.getNickname());
        user.setCipherInfo(ciphertext);

        String salt = BCrypt.gensalt(ConstantInfo.LOG_ROUNDS);
        String password = BCrypt.hashpw(userVo.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);

        service.createUser(user);
        return Result.ok("注册成功！");
    }
}
