package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.entity.UserInfo;

public interface UserService {

    /**
     * 判断登录信息是否正确
     * @param userId
     * @param password
     * @return
     */
    boolean loginCheck(long userId, String password);

    User getUserInfo(long userId, String mode);

    void createUser(User user);

    void updateUserInfo(long userId, UserInfo userinfo);
}
