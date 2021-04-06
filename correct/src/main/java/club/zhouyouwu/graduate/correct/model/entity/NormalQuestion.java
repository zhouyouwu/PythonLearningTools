package club.zhouyouwu.graduate.correct.model.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "normal_question")
@SuppressWarnings("all")
public class NormalQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private long quesId;

    @Column(name = "ques_topic", columnDefinition = "varchar(300)")
    private String quesTopic;

    @Column
    private String quesOptions;//通过&~分隔

    @Column
    private String quesAnswer;

    @Column
    private long quesType;
}
