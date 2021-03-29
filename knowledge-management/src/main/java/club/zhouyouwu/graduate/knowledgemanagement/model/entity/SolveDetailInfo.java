package club.zhouyouwu.graduate.knowledgemanagement.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_solve_detail_info")
public class SolveDetailInfo {
    @Id
    private Long id;

    @Column
    private String answer;

    @Column
    private String replyTime;

    @Column
    private Integer status;
}
