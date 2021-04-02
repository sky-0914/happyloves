package cn.happyloves.example.dto;


import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;

/**
 * @author zc
 * @date 2020/9/4 23:45
 */
@Data
public class Account {
    private long id;
    private String username;
    private String password;

    public static void main(String[] args) {
        Object o1 = new Object();
        System.out.println("空对象：" + ClassLayout.parseInstance(o1).toPrintable());
        Object o2 = new Account();
        System.out.println("带属性对象：" + ClassLayout.parseInstance(o2).toPrintable());
        Object o3 = new int[1];
        System.out.println("数组：" + ClassLayout.parseInstance(o3).toPrintable());
        Object o4 = new ArrayList<>();
        System.out.println("空List：" + ClassLayout.parseInstance(o4).toPrintable());
    }
}
