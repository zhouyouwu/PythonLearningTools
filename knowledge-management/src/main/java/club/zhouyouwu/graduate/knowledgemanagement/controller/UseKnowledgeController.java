package club.zhouyouwu.graduate.knowledgemanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.knowledgemanagement.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("knowledge")
public class UseKnowledgeController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("unsolved")
    public Result getUnsolvedQuestions(long userId, int count){

        return Result.ok(questionService.getUnsolvedQuestions(userId, count));
    }
}
