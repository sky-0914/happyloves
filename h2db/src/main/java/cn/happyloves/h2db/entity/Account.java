package cn.happyloves.h2db.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zc
 * @date 2020/8/19 00:07
 */
@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String username;
    private String password;
}
