package club.zhouyouwu.graduate.correct.serivce;

import club.zhouyouwu.graduate.correct.model.param.CorrectParam;
import club.zhouyouwu.graduate.correct.model.param.QuestionSolve;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CorrectService {
    Map<Integer, QuestionSolve> correctNormalQuestion(List<CorrectParam> param);

    Map<String, String> correctProgramQuestion(CorrectParam param) throws IOException;
}
