package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.constant.JoinClazzOperation;
import club.zhouyouwu.graduate.usermanagement.feign.AnalysisFeign;
import club.zhouyouwu.graduate.usermanagement.feign.KnowledgeFeign;
import club.zhouyouwu.graduate.usermanagement.service.ClazzUtilsService;
import club.zhouyouwu.graduate.usermanagement.service.StudentUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Student")
public class StudentUtilsController {
    @Autowired
    private StudentUtilsService studentUtils;
    @Autowired
    private KnowledgeFeign knowledgeFeign;
    @Autowired
    private AnalysisFeign analysisFeign;
    @Autowired
    private ClazzUtilsService clazzUtils;

    @GetMapping("/{userId}/question")
    public Result getQuestion(@PathVariable long userId, @RequestParam("count") int count) {

        long typeId = analysisFeign.analysisKnowledgeType(userId);
        //调用analysis的方法，判断用户应该侧重哪方面练习 long typeId
        List<Long> quesIds = (List<Long>) knowledgeFeign.getUnsolvedQuestions(userId, count, typeId).getResult();

        return Result.ok(quesIds);
    }

    @PostMapping("/{userId}/{quesId}/answer")
    public Result postAnswer(@PathVariable long userId, @PathVariable int quesId){
        return null;
    }//todo 放到批改模块中

    @PutMapping("/{userId}/clazz/{clazzId}/{operation}")
    public Result joinClazz(@PathVariable long userId, @PathVariable long clazzId, @PathVariable int operation){

        if(operation == JoinClazzOperation.APPLICATION.getValue()){
            int status = studentUtils.studentBelongClazz(userId, clazzId);
            if(status == JoinClazzOperation.APPLICATION.getValue()){
                return Result.failed("您已申请，请耐心等待老师同意");
            }else if(status == JoinClazzOperation.PASS.getValue()){
                return Result.failed("您已加入该班级，无需重复加入");
            }else if(status == JoinClazzOperation.INVITE.getValue()){
                return Result.failed("您收到邀请，请到邀请信息处查看或立刻同意邀请");
            }
            clazzUtils.joinStudent(clazzId, userId);
            return Result.ok("申请成功");

        }else if(operation == JoinClazzOperation.PASS.getValue() ||
                            operation == JoinClazzOperation.REFUSE.getValue()){

            clazzUtils.handleApplication(clazzId, userId, operation);
            return Result.ok("处理成功");
        }
        return Result.failed("操作参数有误，请检查");
    }
}
