package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.entity.Question;
import club.zhouyouwu.graduate.knowledgemanagement.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Override
    public List<Question> getUnsolvedQuestions(long userId, int count) {

        return null;
    }

    @Override
    public List<Question> getUnsolvedQuestions(long userId, int count, long typeId) {
        return null;
    }

    @Override
    public Question getQuestion(long quesId) {
        Question question = new Question();
        question.setQuesTopic("哆啦a梦");
        return question;
    }
}
