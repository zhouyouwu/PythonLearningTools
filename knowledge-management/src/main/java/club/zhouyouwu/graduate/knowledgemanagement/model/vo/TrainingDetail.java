package club.zhouyouwu.graduate.knowledgemanagement.model.vo;

import lombok.Data;

/**
 * 用户解决的问题的详情
 */
@Data
public class TrainingDetail {

    private Long quesId;
    private String quesTopic;
    private String quesOptions;//通过&~分隔
    private String quesAnswer;//问题的答案
    private Long quesType;

    private Long userId;

    private Integer programOrNormal;

    private String answer;//用户的答案

    private String replyTime;

    private Integer status;//此次提交答案的结果
}
