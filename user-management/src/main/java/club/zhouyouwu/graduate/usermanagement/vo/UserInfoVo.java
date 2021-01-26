package club.zhouyouwu.graduate.usermanagement.vo;

import club.zhouyouwu.graduate.usermanagement.entity.Permission;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public class UserInfoVo {
    //作为唯一id
    //必须不为空的
    @Valid
    private String userId;
    private String nickname;
    private String password;
    private String salt;
    private String phoneNo;
    private List<Permission> permissions;

    //可选
    private String signature;
    private MultipartFile profilePhoto;//存储文件，数据库中存储地址

    private String eMail;
    private String sex;
    private String birthday;


}
