package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.constant.JoinClazzOperation;
import club.zhouyouwu.graduate.usermanagement.entity.Clazz;
import club.zhouyouwu.graduate.usermanagement.entity.Student;
import club.zhouyouwu.graduate.usermanagement.service.StudentUtilsService;
import club.zhouyouwu.graduate.usermanagement.service.ClazzUtilsService;
import club.zhouyouwu.graduate.usermanagement.utils.ExcelUtil;
import club.zhouyouwu.graduate.usermanagement.utils.SnowFlake;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiresRoles("teacher")
@RequestMapping("Teacher")
public class TeacherUtilsController {
    @Autowired
    private ClazzUtilsService clazzUtils;
    @Autowired
    private StudentUtilsService studentUtils;

    /**
     * 创建班级
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping("/{userId}/clazz")//todo 老师权限才可以
    public Result createClazz(@PathVariable long userId, @RequestParam(value = "desc", required = false, defaultValue = "") String desc) {
        SnowFlake snowFlake = SnowFlake.getInstance(ConstantInfo.WORKER_ID,ConstantInfo.DATACENTER_ID);
        long clazzId = snowFlake.nextId();

        clazzUtils.createClazz(userId, clazzId, desc);
        return Result.ok("创建成功");
    }

    /**
     * 查询用户所有的班级
     */
    @GetMapping("/{userId}/clazz")
    public Result getClazz(@PathVariable long userId) {

        return Result.ok(clazzUtils.getClazz(userId));
    }

    /**
     * 查询具体某个班级信息
     */
    @GetMapping("/{userId}/clazz/{clazzId}")
    public Result getClazz(@PathVariable("userId") long userId, @PathVariable("clazzId") long clazzId) {

        return Result.ok(clazzUtils.getClazz(userId, clazzId));
    }

    /**
     * 删除班级
     */
    @DeleteMapping("/{userId}/clazz/{clazzId}")
    public Result delClazz(@PathVariable long userId, @PathVariable("clazzId") long clazzId) {

        clazzUtils.delClazz(clazzId);
        return Result.ok("删除成功");
    }

    /**
     * 处理一个学生入班(邀请，通过申请，拒绝申请)
     * @return
     */
    @PutMapping("/{userId}/clazz/{clazzId}/{studentId}/{operation}")
    public Result joinStudent(@PathVariable long userId, @PathVariable long clazzId,
                              @PathVariable long studentId, @PathVariable int operation){

        if (operation == JoinClazzOperation.INVITE.getValue()) {
            int status = studentUtils.studentBelongClazz(userId, clazzId);
            if(status == JoinClazzOperation.INVITE.getValue()){
                return Result.failed("您已邀请，请耐心等待该生接受");
            }else if(status == JoinClazzOperation.PASS.getValue()){
                return Result.failed("该同学已加入该班级，无需重复加入");
            }else if(status == JoinClazzOperation.APPLICATION.getValue()){
                return Result.failed("您收到申请，请到申请信息处查看或立刻同意");
            }
            clazzUtils.joinStudent(clazzId, studentId);
            return Result.ok("邀请学生成功");

        }else if(operation == JoinClazzOperation.PASS.getValue() ||
                                operation == JoinClazzOperation.REFUSE.getValue()){

            clazzUtils.handleApplication(clazzId, studentId, operation);
            return Result.ok("处理成功");
        }

        return Result.failed("操作参数有误，请检查");
    }

    /**
     * 用excel邀请学生入班
     */
    @PutMapping("/{userId}/clazz/{clazzId}/students")
    public Result joinStudent(@PathVariable long userId, @PathVariable long clazzId,
                              @RequestParam("excel") MultipartFile excel) throws IOException {

        XSSFWorkbook workbook = ExcelUtil.getExcel(excel);

        List<Long> studentIds = clazzUtils.getStudentIds(workbook);

        clazzUtils.joinStudent(clazzId, studentUtils.notBelongClazzStudents(studentIds, clazzId));
        return Result.ok("邀请学生成功");
    }

    /**
     * 获取学生学习信息
     * @return
     */
    @GetMapping("/{userId}/clazz/{clazzId}/{studentId}")
    public Result getStudent(@PathVariable long userId, @PathVariable long clazzId, @PathVariable long studentId){

        if(studentUtils.studentBelongClazz(studentId, clazzId) == JoinClazzOperation.PASS.getValue()){
            return Result.ok(studentUtils.getStudentInfo(studentId));
        }
        return Result.failed("不在班级中");
    }

    /**
     * 获取班级学生学习统计
     */
    @GetMapping("/{userId}/clazz/{clazzId}/students")
    public Result getStudent(@PathVariable long userId, @PathVariable long clazzId,
                             @RequestParam("time") String time, @RequestParam(value = "timeSize", defaultValue = "") int timeSize){

        Clazz clazz = clazzUtils.getClazz(userId, clazzId);
        List<Long> studentIds = clazz.getStudents().stream().map(Student::getStudentId).collect(Collectors.toList());

        return Result.ok(studentUtils.getStudentInfo(studentIds, time, timeSize));
    }
}
