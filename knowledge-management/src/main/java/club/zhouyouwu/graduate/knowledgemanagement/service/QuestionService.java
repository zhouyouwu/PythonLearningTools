package club.zhouyouwu.graduate.knowledgemanagement.service;

import club.zhouyouwu.graduate.knowledgemanagement.model.entity.NormalQuestion;
import club.zhouyouwu.graduate.knowledgemanagement.model.entity.ProgramQuestion;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;

import java.util.List;

public interface QuestionService {

    List<NormalQuestion> getUnsolvedQuestions(Long userId, Integer count);

    List<NormalQuestion> getUnsolvedQuestions(Long userId, Integer count, Long typeId);

    ProgramQuestion getProgramQuestion(Long quesId);

    NormalQuestion getNormalQuestion(Long quesId);

    void setQuestion(String model, Question pageData);

    void delQuestion(String model, Long quesId);

    void mdfQuestion(String model, Question pageData);

    List<ProgramQuestion> getUnsolvedProgramQuestions(Long userId, Integer count);

    List<ProgramQuestion> getUnsolvedProgramQuestions(Long userId, Integer count, Long typeId);
}
