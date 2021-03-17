package club.zhouyouwu.graduate.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resp<T> {

    private int code;

    private String mass;

    private T data;
}
