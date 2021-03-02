package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.entity.UserInfo;
import club.zhouyouwu.graduate.usermanagement.mapper.UserMapper;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean loginCheck(long userId, String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(Long.toString(userId), password);
        subject.login(token);

        return true;
    }

    @Override
    public User getUserInfo(long userId, String mode) {
        if("Auth".equals(mode)){
            return userMapper.getUserAuthInfo(userId);
        }
        if("Detail".equals(mode)){
            return userMapper.getUserDetailInfo(userId);
        }
        return null;
    }

    @Override
    public void createUser(User user) {

        userMapper.createUser(user);
    }

    @Override
    public void updateUserInfo(long userId, UserInfo userinfo) {

        userMapper.updateUserInfo(userId, userinfo);
    }
}
