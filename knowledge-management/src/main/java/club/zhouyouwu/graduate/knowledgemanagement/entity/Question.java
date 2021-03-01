package club.zhouyouwu.graduate.knowledgemanagement.entity;

import lombok.Data;

@Data
public class Question {
    private long quesId;
    private String quesTopic;
    private String quesOptions;//通过&~分隔
    private String quesAnswer;
    private long quesType;
}
