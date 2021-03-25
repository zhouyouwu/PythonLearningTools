package club.zhouyouwu.graduate.usermanagement.model.params;

import lombok.Data;

@Data
public class WxLoginParam {

    private String code;

    private UserInfoParam userInfo;
}
