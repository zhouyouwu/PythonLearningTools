package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.model.entity.Student;
import club.zhouyouwu.graduate.usermanagement.mapper.ClazzMapper;
import club.zhouyouwu.graduate.usermanagement.service.StudentUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUtilsServiceImpl implements StudentUtilsService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public int studentBelongClazz(long studentId, long clazzId) {

        return clazzMapper.studentBelongClazz(studentId, clazzId);
    }

    @Override
    public List<Long> notBelongClazzStudents(List<Long> studentIds, long clazzId) {

        return clazzMapper.notBelongClazzStudents(studentIds, clazzId);
    }

    @Override
    public Student getStudentInfo(long studentId) {
        return null;
    }

    @Override
    public Student getStudentInfo(List<Long> studentIds, String time, int timeSize) {
        return null;
    }
}
