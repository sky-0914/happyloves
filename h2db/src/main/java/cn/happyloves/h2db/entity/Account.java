package cn.happyloves.h2db.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zc
 * @date 2020/8/19 00:07
 */
@Data//lombok插件的注解
@Entity//JPA实体类注解
@Table(name = "account")//JPA表映射的注解
public class Account {
    @Id//主键ID注解
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键ID唯一，一版数据库是Mysql时，主键ID自增时这样设置
    private Integer id;

    private String name;
    private String username;
    private String password;
}
