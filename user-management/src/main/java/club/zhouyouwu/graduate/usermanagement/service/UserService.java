package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.vo.UserInfoVo;

import java.util.List;

public interface UserService {
    User getUserInfo(Long userId);

    void createUser(User user);

    void updateUserInfo(Long userId, User user);

    void updateProfilePhoto(Long userId, String fileName);

    void updatePassword(String oldPassword, String newPassword, Long userId);

    void setUserRole(Long userId, String roleName);

    void delUser(long userId);

    List<UserInfoVo> getUserByOpenid(String openid);
}
