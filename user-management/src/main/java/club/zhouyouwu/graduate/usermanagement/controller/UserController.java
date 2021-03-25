package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.params.UserInfoParam;
import club.zhouyouwu.graduate.usermanagement.model.params.WxLoginParam;
import club.zhouyouwu.graduate.usermanagement.model.vo.UserInfoVo;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import club.zhouyouwu.graduate.usermanagement.utils.ImageUtil;
import club.zhouyouwu.graduate.usermanagement.utils.JwtUtil;
import club.zhouyouwu.graduate.usermanagement.utils.SnowFlake;
import club.zhouyouwu.graduate.usermanagement.utils.TimeUtil;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录流程
 * B向S请求公钥，B用得到的公钥向S发送注册信息|登录信息
 * 登录成功后S生成token，用用户明文密钥解锁用户信息同token一起发送RSA，
 * 私钥废弃，B收到token、用户信息存储；
 * 注册成功后直接登录，用用户明文加密用户信息存储AES，返回token及用户信息私钥废弃同上。
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {
    private StringRedisTemplate redisTemplate;
    private UserService userService;
    private WxMaService wxMaService;

    /**
     * 账号密码登录，管理端。（管理端不提供注册，根账户直接创建子账户）
     *
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestParam Long userId, @RequestParam String password) {

        User user = userService.getUserInfo(userId);
        if(user == null){
            return Result.failed("用户不存在");
        }

        String hsPassword = user.getPassword();
        if(!BCrypt.checkpw(password, hsPassword)){

            return Result.failed("密码出错");
        }

        user.setLastLoginTime(TimeUtil.currentTimeConvTo(TimeUtil.timeFormat1));
        String token = JwtUtil.createJWT("3120170911", "graduate", userId.toString(), 1000*60*30);
        userService.updateUserInfo(userId, user);

        //todo 返回的数据有hash后密码，openid，sessionKey考虑换个输出
        Map<String, Object> info = new HashMap<>();
        info.put("user", user);
        info.put("token", token);
        return Result.ok(info, "登录成功");
    }

    /**
     * 微信登录，小程序端
     *
     * @param loginParam
     * @return
     * @throws Exception
     */
    @PostMapping("{role}/login_by_wx")
    public Result loginByWx(@RequestBody WxLoginParam loginParam, @PathVariable String role) throws Exception {

        if (StringUtils.isEmpty(loginParam.getCode())) {
            return Result.failed("参数错误");
        }
        WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(loginParam.getCode());

        List<UserInfoVo> userList = userService.getUserByOpenid(result.getOpenid());
        if (userList.size() > 1) {
            return Result.failed("系统内部错误");
        }
        if (userList.size() == 1) {
            UserInfoVo user = userList.get(0);
            if (user.getRoles().stream().noneMatch(o -> o.getName().equals(role))) {
                userService.setUserRole(user.getUser().getUserId(), role);
            }
            user.getUser().setSessionKey(result.getSessionKey());
            user.getUser().setLastLoginTime(TimeUtil.currentTimeConvTo(TimeUtil.timeFormat1));

            userService.updateUserInfo(user.getUser().getUserId(), user.getUser());

            String token = JwtUtil.createJWT("3120170911", "graduate", user.getUser().getUserId().toString(), 1000*60*30);
            Map<String, Object> info = new HashMap<>();
            info.put("user", user);
            info.put("token", token);
            return Result.ok(info, "登录成功");
        }

        User user = new User();
        UserInfoParam userInfo = loginParam.getUserInfo();
        user.setUserId(SnowFlake.getInstance(ConstantInfo.WORKER_ID, ConstantInfo.DATACENTER_ID).nextId());

        user.setSignature(userInfo.getSignature());
        user.setNickname(userInfo.getNickname());
        user.setPhoneNo(userInfo.getPhoneNo());
        user.setBirthday(userInfo.getBirthday());
        user.setEMail(userInfo.getEMail());
        user.setProfilePhoto(userInfo.getProfilePhoto());
        user.setSex(userInfo.getSex());

        user.setSessionKey(result.getSessionKey());
        user.setLastLoginTime(TimeUtil.currentTimeConvTo(TimeUtil.timeFormat1));
        user.setWxOpenid(result.getOpenid());

        userService.createUser(user);
        userService.setUserRole(user.getUserId(), role);

        String token = JwtUtil.createJWT("3120170911", "graduate", user.getUserId().toString(), 1000*60*30);
        Map<String, Object> info = new HashMap<>();
        info.put("user", user);
        info.put("token", token);
        return Result.ok(info, "登录成功");
    }

    @PostMapping("admin")
    public Result createAdmin() {

        long userId = SnowFlake.getInstance(ConstantInfo.WORKER_ID, ConstantInfo.DATACENTER_ID).nextId();

        String salt = BCrypt.gensalt(ConstantInfo.LOG_ROUNDS);
        String hsPassword = BCrypt.hashpw("asdfghjkl", salt);//默认密码，让用户自己修改

        User user = new User();
        user.setUserId(userId);
        user.setPassword(hsPassword);

        userService.createUser(user);

        user.setPassword("asdfghjkl");//返回明文密码
        return Result.ok(user, "创建管理员成功");
    }


    /**
     * 用户更新自己的信息
     *
     * @param userId
     * @param userInfo
     * @return
     */
    @PutMapping("/{userId}")
    public Result updateInfo(@PathVariable long userId, @RequestBody UserInfoParam userInfo) {

        User user = new User();
        user.setSex(userInfo.getSex());
        user.setProfilePhoto(userInfo.getProfilePhoto());
        user.setEMail(userInfo.getEMail());
        user.setBirthday(userInfo.getBirthday());
        user.setPhoneNo(userInfo.getPhoneNo());
        user.setNickname(userInfo.getNickname());
        user.setSignature(userInfo.getSignature());

        userService.updateUserInfo(userId, user);
        return Result.ok("更新成功");
    }

    @GetMapping("/{userId}")
    public Result getUserInfo(@PathVariable long userId) {

        User user = userService.getUserInfo(userId);
        UserInfoParam param = new UserInfoParam();

        param.setSex(user.getSex());
        param.setProfilePhoto(user.getProfilePhoto());
        param.setEMail(user.getEMail());
        param.setBirthday(user.getBirthday());
        param.setPhoneNo(user.getPhoneNo());
        param.setNickname(user.getNickname());
        param.setSignature(user.getSignature());
        return Result.ok(param);
    }

    @PutMapping("/{userId}/password")
    public Result updatePassword(@PathVariable Long userId, @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {

        User user = userService.getUserInfo(userId);
        if (user == null) {
            return Result.failed("用户不存在");
        }

        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            return Result.failed("密码错误");

        }
        String salt = BCrypt.gensalt(ConstantInfo.LOG_ROUNDS);

        user.setPassword(BCrypt.hashpw(newPassword, salt));
        return Result.ok(user, "密码修改完成");
    }

    @DeleteMapping("/{userId}")
    public Result delUser(@PathVariable long userId) {
        userService.delUser(userId);

        return Result.ok("删除成功");
    }

    @PutMapping("/{userId}/photo")
    public Result updatePhoto(@PathVariable long userId, MultipartFile file) throws IOException {

        String fileName = SnowFlake.getInstance(ConstantInfo.WORKER_ID, ConstantInfo.DATACENTER_ID_FOR_IMAGE).nextId() + ".jpg";
        ImageUtil.storeImage(file, fileName);

        userService.updateProfilePhoto(userId, fileName);
        return Result.ok("头像更换成功");
    }

    /**
     * 通过验证token签名是否是服务端签发，有效期是否还在
     *
     * @param token
     * @return
     */
    @GetMapping("checkTokenResult")
    public Result checkToken(String token) {
        log.info("验证token...");
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            log.info("token无效", e);
            return Result.failed(e.getMessage());
        }

        return Result.ok(JwtUtil.extendTtlMillis(claims, 1000*60*30));
    }
}
