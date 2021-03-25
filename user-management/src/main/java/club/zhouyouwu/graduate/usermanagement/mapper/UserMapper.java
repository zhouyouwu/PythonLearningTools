package club.zhouyouwu.graduate.usermanagement.mapper;

import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import club.zhouyouwu.graduate.usermanagement.model.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    UserInfoVo getUserByUserid(@Param("userId") long userId);

    void updateUserInfo(long userId, User user);
    //仅修改密码，不支持改角色，消耗太大
    void updateUserAuthInfo(User user);

    void createUser(User user);

    int checkHasRole(long userId, String roleName);

    void setRole(long userId, String roleName);

    void delRole(long userId, String roleName);

    void updateProfilePhoto(Long userId, String fileName);

    void delUser(long userId);

    List<UserInfoVo> getUserByOpenid(String openid);
}
