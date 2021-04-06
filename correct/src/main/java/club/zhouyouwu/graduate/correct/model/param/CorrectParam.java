package club.zhouyouwu.graduate.correct.model.param;

import lombok.Data;

@Data
public class CorrectParam {
    private Long userId;
    private Long quesId;
    private String answer;
    private Integer serialNumber;//通过序号排序输出
}
