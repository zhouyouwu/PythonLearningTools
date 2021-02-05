package club.zhouyouwu.graduate.usermanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.usermanagement.constant.ConstantInfo;
import club.zhouyouwu.graduate.usermanagement.entity.Clazz;
import club.zhouyouwu.graduate.usermanagement.entity.Student;
import club.zhouyouwu.graduate.usermanagement.service.StudentUtilsService;
import club.zhouyouwu.graduate.usermanagement.service.TeacherUtilsService;
import club.zhouyouwu.graduate.usermanagement.utils.Excel;
import club.zhouyouwu.graduate.usermanagement.utils.SnowFlake;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Teacher")
public class TeacherUtilsController {
    @Autowired
    private TeacherUtilsService teacherUtils;
    @Autowired
    private StudentUtilsService studentUtils;

    /**
     * 创建班级
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping("/{userId}/clazz")//todo 老师权限才可以
    public Result createClazz(@PathVariable long userId, @RequestParam(value = "desc", required = false) String desc) {
        SnowFlake snowFlake = SnowFlake.getInstance(ConstantInfo.WORKER_ID,ConstantInfo.DATACENTER_ID);
        long clazzId = snowFlake.nextId();

        teacherUtils.createClazz(userId, clazzId, desc);
        return Result.ok("创建成功");
    }

    /**
     * 查询用户所有的班级
     */
    @GetMapping("/{userId}/clazz")
    public Result getClazz(@PathVariable long userId) {

        return Result.ok(teacherUtils.getClazz(userId));
    }

    /**
     * 查询具体某个班级信息
     */
    @GetMapping("/{userId}/clazz/{clazzId}")
    public Result getClazz(@PathVariable("userId") long userId, @PathVariable("clazzId") long clazzId) {

        return Result.ok(teacherUtils.getClazz(userId, clazzId));
    }

    /**
     * 删除班级
     */
    @DeleteMapping("/{userId}/clazz/{clazzId}")
    public Result delClazz(@PathVariable long userId, @PathVariable("clazzId") long clazzId) {

        teacherUtils.delClazz(clazzId);
        return Result.ok("删除成功");
    }

    /**
     * 邀请一个学生入班
     * @return
     */
    @PutMapping("/{userId}/clazz/{clazzId}/{studentId}")
    public Result joinStudent(@PathVariable long userId,
                              @PathVariable long clazzId, @PathVariable long studentId){

        teacherUtils.joinStudent(clazzId, studentId);
        return Result.ok("邀请学生成功");
    }

    /**
     * 用excel邀请学生入班
     */
    @PutMapping("/{userId}/clazz/{clazzId}/students")
    public Result joinStudent(@PathVariable long userId, @PathVariable long clazzId,
                              @RequestParam("excel") MultipartFile excel) throws IOException {

        XSSFWorkbook workbook = Excel.getExcel(excel);

        List<Long> studentIds = teacherUtils.getStudentIds(workbook);
        teacherUtils.joinStudent(clazzId, studentIds);
        return Result.ok("邀请学生成功");
    }

    /**
     * 获取学生学习信息
     * @return
     */
    @GetMapping("/{userId}/clazz/{clazzId}/{studentId}")//todo 不在班级中可以获得学生id吗?
    public Result getStudent(@PathVariable long userId, @PathVariable long clazzId, @PathVariable long studentId){

        if(studentUtils.studentBelongClazz(studentId, clazzId)){
            return Result.ok(studentUtils.getStudentInfo(studentId));
        }
        return Result.failed("不在班级中");//todo 改成返回用户信息，可否？
    }

    /**
     * 获取班级学生学习统计
     */
    @GetMapping("/{userId}/clazz/{clazzId}/students")
    public Result getStudent(@PathVariable long userId, @PathVariable long clazzId,
                             @RequestParam("time") String time, @RequestParam(value = "timeSize", defaultValue = "7") int timeSize){

        Clazz clazz = teacherUtils.getClazz(userId, clazzId);
        List<Long> studentIds = clazz.getStudents().stream().map(Student::getStudentId).collect(Collectors.toList());

        return Result.ok(studentUtils.getStudentInfo(studentIds, time, timeSize));
    }
}
