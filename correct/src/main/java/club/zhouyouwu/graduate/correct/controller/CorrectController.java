package club.zhouyouwu.graduate.correct.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.correct.model.param.CorrectParam;
import club.zhouyouwu.graduate.correct.serivce.CorrectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("Correct")
public class CorrectController {
    @Autowired
    private CorrectService correctService;

    @PostMapping("normal")
    public Result correctNormalQuestion(@RequestBody List<CorrectParam> param){

        return Result.ok(correctService.correctNormalQuestion(param));
    }

    @PostMapping("program")
    public Result correctProgramQuestion(@RequestBody CorrectParam param) throws IOException {

        return Result.ok(correctService.correctProgramQuestion(param));
    }
}
