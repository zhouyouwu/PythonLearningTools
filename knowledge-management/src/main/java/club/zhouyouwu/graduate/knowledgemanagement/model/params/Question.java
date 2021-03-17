package club.zhouyouwu.graduate.knowledgemanagement.model.params;

import lombok.Data;

@Data
public class Question {
    private static final String MODE_NORMAL = "normal";
    private static final String MODE_PROGRAM = "program";

    private long quesId;

    private String quesTopic;

    private String quesOptions;//通过&~分隔

    private String quesAnswer;

    private long quesType;//问题所属知识点单元

    private String quesMode;
}
