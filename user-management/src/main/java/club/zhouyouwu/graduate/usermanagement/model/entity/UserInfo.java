package club.zhouyouwu.graduate.usermanagement.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用密文解密得到的信息
 */
@Data
public class UserInfo implements Serializable {
    private String nickname;
    private String phoneNo;
    private String signature;
    private String profilePhoto;//存储头像文件，在数据库中存储地址
    private String eMail;
    private String sex;
    private String birthday;

    public static void main(String[] args) {
        List<UserInfo> list = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNo("15183631872");
        userInfo.setSignature("sssyyyysss");
        list.add(userInfo);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPhoneNo("17361047427");
        userInfo1.setSignature("yyyHHHHsss");
        list.add(userInfo1);
        System.out.println(list);
        list = list.stream().peek(o->{
            o.setPhoneNo(o.getPhoneNo().substring(0,3)+"*****"+o.getPhoneNo().substring(8));
            o.setSignature(o.getSignature().substring(0,3)+"********");
        }).collect(Collectors.toList());
        System.out.println(list);
    }
}
