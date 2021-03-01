package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.entity.Student;

import java.util.List;

public interface StudentUtilsService {

    int studentBelongClazz(long studentId, long clazzId);

    List<Long> notBelongClazzStudents(List<Long> studentIds, long clazzId);

    //获取学习信息，做题数、学习天数等
    Student getStudentInfo(long studentId);

    Student getStudentInfo(List<Long> studentIds, String time, int timeSize);

}
