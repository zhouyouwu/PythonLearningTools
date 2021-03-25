package club.zhouyouwu.graduate.usermanagement.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据库直接的到的数据
 */
@Data
@Entity
@Table(name = "user_info")
public class User {
    @Id
    private Long userId;

    @Column
    private String wxOpenid;

    @Column
    private String sessionKey;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String phoneNo;

    @Column
    private String signature;

    @Column
    private String profilePhoto;//存储头像文件，在数据库中存储地址

    @Column
    private String eMail;

    @Column
    private String sex;

    @Column
    private String birthday;

    @Column
    private String lastLoginTime;
}
