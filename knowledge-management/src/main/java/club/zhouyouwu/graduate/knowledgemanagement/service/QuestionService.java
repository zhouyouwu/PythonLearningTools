package club.zhouyouwu.graduate.knowledgemanagement.service;

import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getUnsolvedQuestions(long userId, int count);

    List<Question> getUnsolvedQuestions(long userId, int count, long typeId);

    Question getQuestion(long quesId);

    void setQuestion(String model, Question pageData);

    void delQuestion(String model, Long quesId);

    void mdfQuestion(String model, Question pageData);
}
