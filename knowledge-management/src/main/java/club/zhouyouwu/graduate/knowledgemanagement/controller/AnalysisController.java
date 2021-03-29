package club.zhouyouwu.graduate.knowledgemanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.knowledgemanagement.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Analysis")
public class AnalysisController {
    @Autowired
    private AnalysisService analysisService;

    @GetMapping("{userId}")
    public Result getUserTrainingData(@PathVariable Long userId){

        return Result.ok(analysisService.getUserTrainingData(userId));
    }

    @GetMapping("/date/{userId}")
    public Result getUserTrainingDataByDate(@PathVariable Long userId, String startDate, String endDate){

        return Result.ok(analysisService.getUserTrainingDataByDate(userId, startDate, endDate));
    }

    @GetMapping("{clazzId}")
    public Result getClazzTrainingData(@PathVariable Long clazzId){

        return Result.ok();
    }


}
