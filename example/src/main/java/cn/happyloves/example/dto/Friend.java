package cn.happyloves.example.dto;

import lombok.Data;

import java.util.HashMap;

/**
 * @author zc
 * @date 2020/9/4 23:45
 */
@Data
public class Friend {

    private String a;
    private String b;


    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap<String, String>(12);
        for (int i = 0; i < 20; i++) {
            map.put(String.valueOf(i), i + "");
            System.out.println(map);
        }
    }
}
