package club.zhouyouwu.graduate.usermanagement.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class Clazz {
    private long clazzId;
    private long teacherId;
    private String desc;
    private List<Student> students;
}
