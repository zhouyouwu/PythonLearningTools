package club.zhouyouwu.graduate.usermanagement.mapper;

import club.zhouyouwu.graduate.usermanagement.entity.User;
import club.zhouyouwu.graduate.usermanagement.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User getUserAuthInfo(@Param("userId") long userId);

    User getUserDetailInfo(@Param("userId") long userId);

    void updateUserDetailInfo(long userId, UserInfo userinfo);
    //仅修改密码，不支持改角色，消耗太大
    void updateUserAuthInfo(User user);

    void createUser(User user);

    int checkHasRole(long userId, int roleId);

    void setRole(long userId, int roleId);

    void delRole(long userId, int roleId);

    void updateProfilePhoto(Long userId, String fileName);

    void delUser(long userId);
}
