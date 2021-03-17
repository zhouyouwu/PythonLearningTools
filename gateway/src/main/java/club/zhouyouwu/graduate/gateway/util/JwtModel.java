package club.zhouyouwu.graduate.gateway.util;

import lombok.Data;

import java.util.List;

@Data
public class JwtModel {

    private Long userId;

    private List<String> roleIdList;
}
