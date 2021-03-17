package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.entity.UserInfo;
import club.zhouyouwu.graduate.usermanagement.exception.BadRequestException;
import club.zhouyouwu.graduate.usermanagement.mapper.UserMapper;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import cn.hutool.crypto.digest.BCrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean loginCheck(Long userId, String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(Long.toString(userId), password);
        subject.login(token);

        return true;
    }

    /**
     * 获取用户信息，Auth鉴权信息，Detail详细信息
     *
     * @param userId 用户id
     * @param mode   选择Auth|Detail
     * @return
     */
    @Override
    public User getUserInfo(Long userId, String mode) {
        if ("Auth".equals(mode)) {
            return userMapper.getUserAuthInfo(userId);
        }
        if ("Detail".equals(mode)) {
            return userMapper.getUserDetailInfo(userId);
        }
        return null;
    }

    @Override
    public void createUser(User user) {

        userMapper.createUser(user);
    }

    @Override
    public void updateUserInfo(Long userId, UserInfo userinfo) {

        userMapper.updateUserDetailInfo(userId, userinfo);
    }

    @Override
    public void updateProfilePhoto(Long userId, String fileName) {

        userMapper.updateProfilePhoto(userId, fileName);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword, Long userId) {

        if (oldPassword.equals(newPassword)) {
            throw new BadRequestException("新密码和旧密码不能相同");
        }

        User user = userMapper.getUserAuthInfo(userId);

        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new BadRequestException("旧密码错误").setErrorData(oldPassword);
        }

        String salt = BCrypt.gensalt(ConstantInfo.LOG_ROUNDS);
        user.setPassword(BCrypt.hashpw(newPassword, salt));

        userMapper.updateUserAuthInfo(user);

//        eventPublisher.publishEvent(
//                new LogEvent(this, updatedUser.getId().toString(), LogType.PASSWORD_UPDATED,
//                        HaloUtils.desensitize(oldPassword, 2, 1)));
    }

    @Override
    public void setUserRole(Long userId, Integer roleId) {

        if (userMapper.checkHasRole(userId, roleId) > 0) {
            throw new BadRequestException("用户已有该角色");
        }
        userMapper.setRole(userId, roleId);
    }

    @Override
    public void delUser(long userId) {
        userMapper.delUser(userId);
    }
}
