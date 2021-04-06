package club.zhouyouwu.graduate.correct.model.param;

import lombok.Data;

@Data
public class QuestionSolve {
    public static final Integer PROGRAM = 1;
    public static final Integer Normal = 0;

    public static final Integer PASS = 1;//当前答案正确或者曾经正确
    public static final Integer FAIL = -1;//失败，没有一次提交有正确答案

    private Long id;
    private Long quesId;
    private Long userId;
    private Integer status;//（detail与total公用，赋值时要区分）
    private Integer programOrNormal;
    private String answer;
    private String replyTime;
}
