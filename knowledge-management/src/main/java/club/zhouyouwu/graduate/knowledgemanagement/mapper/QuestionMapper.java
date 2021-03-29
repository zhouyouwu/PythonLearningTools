package club.zhouyouwu.graduate.knowledgemanagement.mapper;

import club.zhouyouwu.graduate.knowledgemanagement.model.entity.NormalQuestion;
import club.zhouyouwu.graduate.knowledgemanagement.model.entity.ProgramQuestion;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    List<NormalQuestion> getUnsolvedQuestions(@Param("userId") Long userId, @Param("count") Integer count,
                                              @Param("typeId") Long typeId);

    NormalQuestion getNormalQuestionById(Long quesId);

    ProgramQuestion getProgramQuestionById(Long quesId);

    long setNormalQuestion(Question question);

    long setProgramQuestion(Question question);

    void delNormalQuestion(Long quesId);

    void delProgramQuestion(Long quesId);

    void mdfNormalQuestion(Question question);

    void mdfProgramQuestion(Question question);

    List<ProgramQuestion> getUnsolvedProgramQuestions(@Param("userId") Long userId, @Param("count") Integer count,
                                                      @Param("typeId") Long typeId);

    int normalQuesCount();

    int programQuesCount();
}
