package club.zhouyouwu.graduate.correct.mapper;

import club.zhouyouwu.graduate.correct.model.entity.NormalQuestion;
import club.zhouyouwu.graduate.correct.model.param.QuestionSolve;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CorrectMapper {

    List<NormalQuestion> getQuestions(@Param("quesIds") List<Long> quesIds);

    //创建
    void setCorrectOption(@Param("solve") QuestionSolve solve);

    //创建detail只能添加，没有修改
    void setCorrectOptionDetail(@Param("solve") QuestionSolve solve);

    QuestionSolve getQuestionSolve(@Param("userId") Long userId, @Param("quesId") Long quesId, @Param("model") Integer model);

    //仅修改状态，有值则改变，没值不变
    void updateCorrectOption(@Param("solve") QuestionSolve solve);

    Long getQuestionSolveId(@Param("userId") Long userId, @Param("quesId") Long quesId, @Param("model") Integer model);
}
