package club.zhouyouwu.graduate.usermanagement.mapper;

import club.zhouyouwu.graduate.usermanagement.model.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClazzMapper {

    void createClazz(@Param("userId") long userId, @Param("clazzId") long clazzId, @Param("desc") String desc);

    /**
     * 查询老师拥有的班级 //todo 学生信息需不需要展示？
     *
     * @param userId  老师id
     * @param clazzId 班级id 指定此参数则显示此班级，此参数为null老师所有班级
     * @return
     */
    List<Clazz> getClazz(@Param("userId") long userId, @Param("clazzId") Long clazzId);

    void delClazz(@Param("clazzId") long clazzId);

    void joinStudent(@Param("studentId") long studentId, @Param("clazzId") long clazzId);

    void joinStudents(@Param("studentIds") List<Long> studentIds, @Param("clazzId") long clazzId);

    int studentBelongClazz(@Param("studentId") long studentId, @Param("clazzId") long clazzId);

    List<Long> notBelongClazzStudents(@Param("studentIds") List<Long> studentIds, @Param("clazzId") long clazzId);

    void handleApplication(@Param("studentId") long studentId, @Param("clazzId") long clazzId, @Param("operation") int operation);
}
