package club.zhouyouwu.graduate.usermanagement.entity;

import lombok.Data;

import java.util.List;

/**
 * 数据库直接的到的数据
 */
@Data
public class User {
    private long userId;
    private String nickname;
    private String password;
    private String salt;
    private List<Permission> permissions;
    private String cipherInfo;//这个解析其他的隐私
}
