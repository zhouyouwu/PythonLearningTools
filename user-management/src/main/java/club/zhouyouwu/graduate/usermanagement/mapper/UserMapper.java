package club.zhouyouwu.graduate.usermanagement.mapper;

import club.zhouyouwu.graduate.usermanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User getUserInfo(@Param("userId") long userId);
}
