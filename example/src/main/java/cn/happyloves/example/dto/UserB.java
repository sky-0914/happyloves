package cn.happyloves.example.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zc
 * @date 2020/9/4 23:43
 */
@Data
public class UserB {
    private long id;
    private String name;
    private int age;
    private Account account;

    private List<Friend> friends;

    private String b;
}
