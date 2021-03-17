package club.zhouyouwu.graduate.knowledgemanagement.mapper;

import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    List<Question> getUnsolvedQuestions(@Param("userId") Long userId, @Param("count") Integer count,
                                        @Param("typeId") Long typeId);

    Question getNormalQuestionById(Long quesId);

    Question getProgramQuestionById(Long quesId);

    long setNormalQuestion(Question question);

    long setProgramQuestion(Question question);

    void delNormalQuestion(Long quesId);

    void delProgramQuestion(Long quesId);

    void mdfNormalQuestion(Question question);

    void mdfProgramQuestion(Question question);
}
