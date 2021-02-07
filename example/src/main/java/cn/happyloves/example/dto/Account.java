package cn.happyloves.example.dto;


import com.google.common.collect.Lists;
import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

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
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
