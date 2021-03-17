package club.zhouyouwu.graduate.usermanagement.model.vo;

import lombok.Data;

@Data
//注册时使用的收集类
public class UserInfoVo {
    private long userId;
    private String nickname;
    private String password;
    private String phoneNo;
    private String profilePhoto;//存储地址(用于返回，不用于接收)
    private String eMail;
}
