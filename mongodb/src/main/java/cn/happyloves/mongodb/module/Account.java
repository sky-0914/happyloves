package cn.happyloves.mongodb.module;

import lombok.Data;

/**
 * @author ZC
 * @date 2021/4/2 13:55
 */
@Data
public class Account extends User {
    private String name;
    private int age;
    private String sex;

    @ColumnName("home_add")
    private String familyAddress;
    private String idNo;
    private Account accountInfo;
    private TestA testA;
}
