package club.zhouyouwu.graduate.correct.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "program_question")
@SuppressWarnings("all")
public class ProgramQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private long quesId;

    @Column(name = "ques_topic", columnDefinition = "varchar(300)")
    private String quesTopic;

    @Column
    private long quesType;
}
