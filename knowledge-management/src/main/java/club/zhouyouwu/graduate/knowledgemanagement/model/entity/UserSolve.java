package club.zhouyouwu.graduate.knowledgemanagement.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_question_solve")
public class UserSolve {
    @Id
    private Long id;
    @Column
    private Long quesId;
    @Column
    private Long userId;
    @Column
    private Integer status;
    @Column
    private Integer programOrNormal;
}
