package club.zhouyouwu.graduate.knowledgemanagement.model.vo;

import lombok.Data;

@Data
public class TrainingData {
    private Integer totalQuestion;
    private Integer solvedQuestion;
    private Integer unSolvedQuestion;//未解决，即出错
}
