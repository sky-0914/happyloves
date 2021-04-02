package cn.happyloves.mongodb.module;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author ZC
 * @date 2021/4/2 13:55
 */
@Data
public class Account {

    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private int age;
    private String sex;

    @TableField("home_add")
    private String familyAddress;
    private String idNo;
    private Account accountInfo;
}
