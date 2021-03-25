package club.zhouyouwu.graduate.gateway.dto;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Object result;
}
