package club.zhouyouwu.graduate.usermanagement.model.params;

import lombok.Data;

@Data
public class UserInfoParam {
    private String nickname;
    private String phoneNo;
    private String signature;
    private String profilePhoto;//存储头像文件，在数据库中存储地址
    private String eMail;
    private String sex;
    private String birthday;
}
