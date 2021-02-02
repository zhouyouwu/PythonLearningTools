package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.mapper.UserMapper;
import club.zhouyouwu.graduate.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper dao;

    @Override
    public boolean loginCheck(long userId, String password) {
        User user = dao.getUserInfo(userId);

        return true;
    }

    @Override
    public User getUserInfo(long userId, String password) {
        return null;
    }

    @Override
    public void createUser(User user) {

    }
}
