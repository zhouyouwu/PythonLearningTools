package club.zhouyouwu.graduate.usermanagement.service;

import club.zhouyouwu.graduate.usermanagement.model.entity.Clazz;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface ClazzUtilsService {
    void createClazz(long userId, long clazzId, String desc);

    List<Clazz> getClazz(long userId);

    Clazz getClazz(long userId, long clazzId);

    void delClazz(long clazzId);

    void joinStudent(long clazzId, long studentId);

    void joinStudent(long clazzId, List<Long> studentIds);

    /**
     * 从excel中获取学生id
     * @param workbook 学生id必须在第一个工作表，第一列
     * @return
     */
    List<Long> getStudentIds(XSSFWorkbook workbook);

    void handleApplication(long clazzId, long studentId, int operation);
}
