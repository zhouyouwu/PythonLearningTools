package club.zhouyouwu.graduate.knowledgemanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.knowledgemanagement.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Knowledge")
public class UseKnowledgeController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("unsolved")
    public Result getUnsolvedQuestions(Long userId, Integer count){

        return Result.ok(questionService.getUnsolvedQuestions(userId, count));
    }

    @GetMapping("unsolved/{typeId}")
    public Result getUnsolvedQuestions(Long userId, Integer count, @PathVariable Long typeId){

        return Result.ok(questionService.getUnsolvedQuestions(userId, count, typeId));
    }

    @GetMapping("unsolvedProgram")
    public Result getUnsolvedProgramQuestions(Long userId, Integer count){

        return Result.ok(questionService.getUnsolvedProgramQuestions(userId, count));
    }

    @GetMapping("unsolvedProgram/{typeId}")
    public Result getUnsolvedProgramQuestions(Long userId, Integer count, @PathVariable Long typeId){

        return Result.ok(questionService.getUnsolvedProgramQuestions(userId, count, typeId));
    }

}
