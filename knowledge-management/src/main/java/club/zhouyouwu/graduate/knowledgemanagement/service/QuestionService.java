package club.zhouyouwu.graduate.knowledgemanagement.service;

import club.zhouyouwu.graduate.knowledgemanagement.entity.Question;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient
public interface QuestionService {

    List<Long> getUnsolvedQuestion(long userId);

    Question getQuestion(long quesId);
}
