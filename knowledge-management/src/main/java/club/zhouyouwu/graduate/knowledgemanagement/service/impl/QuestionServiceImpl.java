package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.mapper.QuestionMapper;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;
import club.zhouyouwu.graduate.knowledgemanagement.model.entity.*;
import club.zhouyouwu.graduate.knowledgemanagement.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Question> getUnsolvedQuestions(long userId, int count) {

        questionMapper.getUnsolvedQuestions(userId, count, null);
        return null;
    }

    @Override
    public List<Question> getUnsolvedQuestions(long userId, int count, long typeId) {
        questionMapper.getUnsolvedQuestions(userId, count, typeId);
        return null;
    }

    @Override
    public void setQuestion(String model, Question question) {

        if("normal".equals(model)){
            Knowledge knowledge = new Knowledge();
            questionMapper.setNormalQuestion(question);
        }else {
            questionMapper.setProgramQuestion(question);
        }
    }

    @Override
    public Question getQuestion(long quesId) {
        Question question = new Question();
        question.setQuesTopic("哆啦a梦");
        return question;
    }

    @Override
    public void delQuestion(String model, Long quesId) {

        if("normal".equals(model)){
            questionMapper.delNormalQuestion(quesId);
        }else {
            questionMapper.delProgramQuestion(quesId);
        }

    }

    @Override
    public void mdfQuestion(String model, Question question) {

        if("normal".equals(model)){
            questionMapper.mdfNormalQuestion(question);
        }else {
            questionMapper.mdfProgramQuestion(question);
        }
    }
}
