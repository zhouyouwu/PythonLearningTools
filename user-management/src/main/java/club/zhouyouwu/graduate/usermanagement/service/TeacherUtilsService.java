package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.entity.Clazz;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface TeacherUtilsService {
    void createClazz(long userId, long clazzId, String desc);

    List<Clazz> getClazz(long userId);

    Clazz getClazz(long userId, long clazzId);

    void delClazz(long clazzId);

    void joinStudent(long clazzId, long studentId);

    void joinStudent(long clazzId, List<Long> studentIds);

    List<Long> getStudentIds(XSSFWorkbook workbook);
}
