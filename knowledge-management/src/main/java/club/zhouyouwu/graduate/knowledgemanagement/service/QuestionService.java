package club.zhouyouwu.graduate.knowledgemanagement.service;

import club.zhouyouwu.graduate.knowledgemanagement.entity.Question;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

public interface QuestionService {

    List<Question> getUnsolvedQuestions(long userId, int count);

    List<Question> getUnsolvedQuestions(long userId, int count, long typeId);

    Question getQuestion(long quesId);
}
