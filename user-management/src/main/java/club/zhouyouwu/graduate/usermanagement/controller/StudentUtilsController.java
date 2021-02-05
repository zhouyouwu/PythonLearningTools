package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.service.StudentUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Student")
public class StudentUtilsController {
    @Autowired
    private StudentUtilsService studentUtils;

    @GetMapping("/{userId}/question")
    public Result getQuestion(@PathVariable long userId){

        //调用analysis的方法，判断用户应该侧重哪方面练习 long typeId

        return null;
    }
}
