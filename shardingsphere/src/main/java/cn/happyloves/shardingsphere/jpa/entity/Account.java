package cn.happyloves.shardingsphere.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zc
 * @date 2020/9/17 17:22
 */
@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = -1132915818521200568L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String name;
    private int age;
    private String no;
    private String phone;
}
