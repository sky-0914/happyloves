package cn.happyloves.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ExampleApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(8);
        for (int i = 0; i < 20; i++) {
            map.put(i + "", i);
        }
        System.out.println(map);
    }
}
