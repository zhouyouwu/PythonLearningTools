package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.mapper.AnalysisMapper;
import club.zhouyouwu.graduate.knowledgemanagement.mapper.QuestionMapper;
import club.zhouyouwu.graduate.knowledgemanagement.model.entity.UserSolve;
import club.zhouyouwu.graduate.knowledgemanagement.model.vo.TrainingData;
import club.zhouyouwu.graduate.knowledgemanagement.model.vo.TrainingDetail;
import club.zhouyouwu.graduate.knowledgemanagement.service.AnalysisService;
import club.zhouyouwu.graduate.knowledgemanagement.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private AnalysisMapper mapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Map<String, Object> getUserTrainingData(Long userId) {

        List<UserSolve> userSolves = mapper.getUserTrainingData(userId);
        int progCount = questionMapper.programQuesCount();
        int normalCount = questionMapper.normalQuesCount();

        int solveQuestion = 0;
        int unSolveQuestion = 0;
        int solveQuestionProg = 0;
        int unSolveQuestionProg = 0;
        for(UserSolve solve:userSolves){
            if(solve.getProgramOrNormal() == 1){
                if(solve.getStatus() == 1){
                    solveQuestionProg++;
                }else {
                    unSolveQuestionProg++;
                }
            }else {
                if(solve.getStatus() == 1){
                    solveQuestion++;
                }else {
                    unSolveQuestion++;
                }
            }
        }

        TrainingData normal = new TrainingData();
        normal.setSolvedQuestion(solveQuestion);
        normal.setUnSolvedQuestion(unSolveQuestion);
        normal.setTotalQuestion(normalCount);

        TrainingData program = new TrainingData();
        program.setSolvedQuestion(solveQuestionProg);
        program.setUnSolvedQuestion(unSolveQuestionProg);
        program.setTotalQuestion(progCount);

        Map<String, Object> map = new HashMap<>();
        map.put("normalData", normal);
        map.put("programData", program);
        return map;
    }

    @Override
    public Map<String, List<TrainingDetail>> getUserTrainingDataByDate(Long userId, String startDate, String endDate) {

        List<String> dateList = DateUtil.calcTimeRange(startDate, endDate);

        List<TrainingDetail> details = mapper.getUserTrainingByDate(userId, dateList);

        return details.stream().collect(Collectors.groupingBy(TrainingDetail::getReplyTime));
    }
}
