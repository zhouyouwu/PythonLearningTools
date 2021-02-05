package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.entity.Clazz;
import club.zhouyouwu.graduate.usermanagement.service.TeacherUtilsService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TeacherUtilsServiceImpl implements TeacherUtilsService {

    @Override
    public void createClazz(long userId, long clazzId, String desc) {
        //直接调数据库新增一条
    }

    @Override
    public List<Clazz> getClazz(long userId) {
        return null;
    }

    @Override
    public Clazz getClazz(long userId, long clazzId) {
        return null;
    }

    @Override
    public void delClazz(long clazzId) {

    }

    @Override
    public void joinStudent(long clazzId, long studentId) {

    }

    @Override
    public void joinStudent(long clazzId, List<Long> studentIds) {
        //验证学生是否存在
        //给所有存在的学生发送邀请
        //没有成功的要返回
    }

    @Override
    public List<Long> getStudentIds(XSSFWorkbook workbook) {
        List<Long> studentIds = new LinkedList<>();
        Sheet sheet = workbook.getSheetAt(0);

        int rowNum = sheet.getPhysicalNumberOfRows();

        for(int i=1; i<rowNum; i++){
            Cell cell = sheet.createRow(i).getCell(0);
            long id = Long.parseLong(cell.getStringCellValue());
            studentIds.add(id);
        }

        return studentIds;
    }
}
