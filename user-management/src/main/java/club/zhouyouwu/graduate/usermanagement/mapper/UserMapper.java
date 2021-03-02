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

    void updateUserInfo(long userId, UserInfo userinfo);

    void createUser(User user);
}
