package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.entity.Student;
import club.zhouyouwu.graduate.usermanagement.service.StudentUtilsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUtilsServiceImpl implements StudentUtilsService {
    @Override
    public boolean studentBelongClazz(long studentId, long clazzId) {
        return false;
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
