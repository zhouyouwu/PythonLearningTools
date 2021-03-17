package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.entity.UserInfo;

public interface UserService {

    /**
     * 判断登录信息是否正确
     * @param userId
     * @param password
     * @return
     */
    boolean loginCheck(Long userId, String password);

    User getUserInfo(Long userId, String mode);

    void createUser(User user);

    void updateUserInfo(Long userId, UserInfo userinfo);

    void updateProfilePhoto(Long userId, String fileName);

    void updatePassword(String oldPassword, String newPassword, Long userId);

    void setUserRole(Long userId, Integer roleId);

    void delUser(long userId);
}
