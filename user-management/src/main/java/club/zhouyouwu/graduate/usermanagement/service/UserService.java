package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.entity.User;

public interface UserService {

    boolean loginCheck(long userId, String password);

    User getUserInfo(long userId, String password);

    void createUser(User user);
}
