package club.zhouyouwu.graduate.correct.serivce.impl;

import club.zhouyouwu.graduate.correct.mapper.CorrectMapper;
import club.zhouyouwu.graduate.correct.model.entity.NormalQuestion;
import club.zhouyouwu.graduate.correct.model.param.CorrectParam;
import club.zhouyouwu.graduate.correct.model.param.QuestionSolve;
import club.zhouyouwu.graduate.correct.serivce.CorrectService;
import club.zhouyouwu.graduate.correct.util.CorrectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CorrectServiceImpl implements CorrectService {
    @Autowired
    private CorrectMapper correctMapper;

    @Transactional
    @Override
    public Map<Integer, QuestionSolve> correctNormalQuestion(List<CorrectParam> param) {
        String replyTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        List<Long> quesIds = param.stream().map(CorrectParam::getQuesId).collect(Collectors.toList());
        Map<Long, CorrectParam> quesMap = param.stream().collect(Collectors.toMap(CorrectParam::getQuesId, c -> c));

        List<NormalQuestion> questions = correctMapper.getQuestions(quesIds);

        Map<Integer, QuestionSolve> map = new TreeMap<>();

        for (NormalQuestion question : questions) {
            CorrectParam userParam = quesMap.get(question.getQuesId());

            if (userParam == null) {
                continue;
            }
            QuestionSolve solve = correctMapper.getQuestionSolve(userParam.getUserId(), userParam.getQuesId(), QuestionSolve.Normal);
            if(solve == null){
                solve = new QuestionSolve();
                solve.setQuesId(userParam.getQuesId());
                solve.setUserId(userParam.getUserId());
                solve.setProgramOrNormal(QuestionSolve.Normal);
                solve.setReplyTime(replyTime);
                solve.setAnswer(userParam.getAnswer());

                if (question.getQuesAnswer().equals(userParam.getAnswer())) {
                    solve.setStatus(QuestionSolve.PASS);
                } else {
                    solve.setStatus(QuestionSolve.FAIL);
                }
                correctMapper.setCorrectOption(solve);

                //获取插入的数据，将主键赋值
                long id = correctMapper.getQuestionSolveId(solve.getUserId(), solve.getQuesId(), QuestionSolve.Normal);
                solve.setId(id);
                correctMapper.setCorrectOptionDetail(solve);

                map.put(userParam.getSerialNumber(), solve);
                continue;
            }

            if (question.getQuesAnswer().equals(userParam.getAnswer()) && !solve.getStatus().equals(QuestionSolve.PASS)) {
                solve.setStatus(QuestionSolve.PASS);
                correctMapper.updateCorrectOption(solve);
            }
            solve.setReplyTime(replyTime);
            solve.setAnswer(userParam.getAnswer());
            solve.setStatus(question.getQuesAnswer().equals(userParam.getAnswer()) ? QuestionSolve.PASS : QuestionSolve.FAIL);

            correctMapper.setCorrectOptionDetail(solve);
            map.put(userParam.getSerialNumber(), solve);
        }

        return map;
    }

    @Transactional
    @Override//想办法将程序限制在安全区域里，同时限定其内存、时间
    public Map<String, String> correctProgramQuestion(CorrectParam param) throws IOException {
        String replyTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String filePath = "F:/usr/local/hudk/appdata/PLT/answer/" + param.getQuesId();
        String fileName = param.getUserId() + "_" + replyTime + ".py";
        File file = new File(filePath);
        if(!file.exists())
            file.mkdirs();

        FileOutputStream out = new FileOutputStream(filePath+"/"+fileName);
        out.write(param.getAnswer().getBytes());

        Map<String, String> result = CorrectUtil.correctProgramQuestion(filePath+"/"+fileName);

        QuestionSolve solve = correctMapper.getQuestionSolve(param.getUserId(), param.getQuesId(), QuestionSolve.PROGRAM);

        if (solve == null) {
            QuestionSolve newSolve = new QuestionSolve();
            newSolve.setQuesId(param.getQuesId());
            newSolve.setUserId(param.getUserId());
            newSolve.setStatus(result.get("answered").equals("true") ? QuestionSolve.PASS : QuestionSolve.FAIL);
            newSolve.setProgramOrNormal(QuestionSolve.PROGRAM);
            //添加一条总体记录同时返回主键
            correctMapper.setCorrectOption(newSolve);

            long id = correctMapper.getQuestionSolveId(newSolve.getUserId(), newSolve.getQuesId(), QuestionSolve.PROGRAM);

            newSolve.setId(id);
            newSolve.setAnswer(filePath+"/"+fileName);
            newSolve.setReplyTime(replyTime);
            correctMapper.setCorrectOptionDetail(newSolve);

            return result;
        }

        //只有第一次答题成功才修改total状态
        if (result.get("answered").equals("true") && !solve.getStatus().equals(QuestionSolve.PASS)) {
            solve.setStatus(QuestionSolve.PASS);
            correctMapper.updateCorrectOption(solve);
        }

        solve.setAnswer(filePath+"/"+fileName);
        solve.setReplyTime(replyTime);
        solve.setStatus(result.get("answered").equals("true") ? QuestionSolve.PASS : QuestionSolve.FAIL);

        correctMapper.setCorrectOptionDetail(solve);
        return result;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:/Users/方鸿渐/Desktop/Jacobi.py";
        String cmd = "cmd.exe /c python C:/Users/方鸿渐/Desktop/Jacobi.py";

    }
}
