package club.zhouyouwu.graduate.knowledgemanagement.model.params;

import lombok.Data;

@Data
public class SetTypeParam {
    private String typeName;
    private String typeDes;
    private Integer typeLevel;
    private Integer linkTypeId;
    private Integer linkTypeLevel;
}
