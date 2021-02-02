package club.zhouyouwu.graduate.knowledgemanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UseKnowledgeController {

    @GetMapping("/{userId}/question")
    public Result getQuestion(@PathVariable long userId){

        return null;
    }
}
