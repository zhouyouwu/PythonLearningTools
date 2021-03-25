package club.zhouyouwu.graduate.usermanagement.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    private Integer id;

    @Column
    private String name;
    @Column
    private String description;
}
