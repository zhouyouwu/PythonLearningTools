package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.mapper.QuestionMapper;
import club.zhouyouwu.graduate.knowledgemanagement.model.entity.NormalQuestion;
import club.zhouyouwu.graduate.knowledgemanagement.model.entity.ProgramQuestion;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;
import club.zhouyouwu.graduate.knowledgemanagement.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<NormalQuestion> getUnsolvedQuestions(Long userId, Integer count) {

        return questionMapper.getUnsolvedQuestions(userId, count, null);
    }

    @Override
    public List<NormalQuestion> getUnsolvedQuestions(Long userId, Integer count, Long typeId) {

        return questionMapper.getUnsolvedQuestions(userId, count, typeId);
    }

    @Override
    public void setQuestion(String model, Question question) {

        if ("normal".equals(model)) {
            questionMapper.setNormalQuestion(question);
        } else {
            questionMapper.setProgramQuestion(question);
        }
    }

    @Override
    public NormalQuestion getNormalQuestion(Long quesId) {

        return questionMapper.getNormalQuestionById(quesId);
    }

    @Override
    public ProgramQuestion getProgramQuestion(Long quesId) {

        return questionMapper.getProgramQuestionById(quesId);
    }

    @Override
    public void delQuestion(String model, Long quesId) {

        if ("normal".equals(model)) {
            questionMapper.delNormalQuestion(quesId);
        } else {
            questionMapper.delProgramQuestion(quesId);
        }

    }

    @Override
    public void mdfQuestion(String model, Question question) {

        if ("normal".equals(model)) {
            questionMapper.mdfNormalQuestion(question);
        } else {
            questionMapper.mdfProgramQuestion(question);
        }
    }

    @Override
    public List<ProgramQuestion> getUnsolvedProgramQuestions(Long userId, Integer count) {

        return questionMapper.getUnsolvedProgramQuestions(userId, count, null);
    }

    @Override
    public List<ProgramQuestion> getUnsolvedProgramQuestions(Long userId, Integer count, Long typeId) {
        return questionMapper.getUnsolvedProgramQuestions(userId, count, typeId);
    }
}
