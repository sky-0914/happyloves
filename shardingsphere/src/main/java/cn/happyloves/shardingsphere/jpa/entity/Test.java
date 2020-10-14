package cn.happyloves.shardingsphere.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zc
 * @date 2020/9/17 17:22
 */
@Data
@Entity
@Table(name = "test")
public class Test implements Serializable {

    private static final long serialVersionUID = -1132915818521200568L;

    //    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String name;
    private Integer age;
}
