package club.zhouyouwu.graduate.usermanagement.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class Clazz {
    private Long clazzId;
    private Long teacherId;
    private String desc;
    private List<Student> students;
}
