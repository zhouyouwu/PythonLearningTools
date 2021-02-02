package club.zhouyouwu.graduate.usermanagement.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    private long userId;
    private String nickname;
    private String password;
    private String phoneNo;
    //后面赋予
    //private List<Permission> permissions;
    //private String salt;
    //可选
    private String signature;
    private String profilePhoto;//存储地址(用于返回，不用于接收)
    private String eMail;
    private String sex;
    private String birthday;
}
