package club.zhouyouwu.graduate.usermanagement.service.impl;

import club.zhouyouwu.graduate.usermanagement.entity.Clazz;
import club.zhouyouwu.graduate.usermanagement.mapper.ClazzMapper;
import club.zhouyouwu.graduate.usermanagement.service.ClazzUtilsService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ClazzUtilsServiceImpl implements ClazzUtilsService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public void createClazz(long userId, long clazzId, String desc) {
        //直接调数据库新增一条
        clazzMapper.createClazz(userId, clazzId, desc);
    }

    @Override
    public List<Clazz> getClazz(long userId) {

        return clazzMapper.getClazz(userId, null);
    }

    @Override
    public Clazz getClazz(long userId, long clazzId) {

        return clazzMapper.getClazz(userId, clazzId).get(0);
    }

    @Override
    public void delClazz(long clazzId) {

        clazzMapper.delClazz(clazzId);
    }

    @Override
    public void joinStudent(long clazzId, long studentId) {

        clazzMapper.joinStudent(clazzId, studentId);
    }

    @Override
    public void handleApplication(long clazzId, long studentId, int operation) {

        clazzMapper.handleApplication(clazzId, studentId, operation);
    }

    /**
     * 批量要请学生加入
     * @param clazzId 班级
     * @param studentIds 不属于班级的学生才进入此方法
     */
    @Override
    public void joinStudent(long clazzId, List<Long> studentIds) {

        clazzMapper.joinStudents(studentIds, clazzId);
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
