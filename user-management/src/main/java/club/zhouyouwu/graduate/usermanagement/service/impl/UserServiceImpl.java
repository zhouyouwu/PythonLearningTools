package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.exception.BadRequestException;
import club.zhouyouwu.graduate.usermanagement.mapper.UserMapper;
import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.vo.UserInfoVo;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户信息，Auth鉴权信息，Detail详细信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public User getUserInfo(Long userId) {

        return userMapper.getUserByUserid(userId).getUser();
    }

    @Override
    public void createUser(User user) {

        userMapper.createUser(user);
    }

    @Override
    public void updateUserInfo(Long userId, User user) {

        userMapper.updateUserInfo(userId, user);
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

        User user = userMapper.getUserByUserid(userId).getUser();

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
    public void setUserRole(Long userId, String roleName) {

        if (userMapper.checkHasRole(userId, roleName) > 0) {
            throw new BadRequestException("用户已有该角色");
        }
        userMapper.setRole(userId, roleName);
    }

    @Override
    public void delUser(long userId) {
        userMapper.delUser(userId);
    }

    @Override
    public List<UserInfoVo> getUserByOpenid(String openid) {

        return userMapper.getUserByOpenid(openid);
    }
}
