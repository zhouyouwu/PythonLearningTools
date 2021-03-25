package club.zhouyouwu.graduate.usermanagement.model.vo;

import club.zhouyouwu.graduate.usermanagement.model.entity.Role;
import club.zhouyouwu.graduate.usermanagement.model.entity.User;
import lombok.Data;

import java.util.List;

@Data
//用户信息+权限
public class UserInfoVo {

    private User user;

    private List<Role> roles;
}
