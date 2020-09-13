package cn.happyloves.redis;

import cn.happyloves.redis.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    RedisUtils redisUtils;

    @Test
    void redis() {
//        redisUtils.set("abc", "abc");
//        redisUtils.set("aaa", "aaa");
//        redisUtils.set("bbb", "bbb");
//        redisUtils.set("ccc", "ccc");
//        System.out.println(redisUtils.keys("abc"));
//        System.out.println(redisUtils.keys("*a*"));
//        System.out.println(redisUtils.keys("*b*"));

        Student s1 = Student.builder()
                .name("张三")
                .age(18)
                .build();
        Student s2 = Student.builder()
                .name("李四")
                .age(19)
                .build();

        redisUtils.set("s1", s1);
        redisUtils.set("s2", s2);

        System.out.println(redisUtils.keys("s*"));
        System.out.println(redisUtils.get("s1"));

        redisUtils.expire("s1", 10);
        System.out.println(redisUtils.getExpire("s1"));

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Student implements Serializable {
        private static final long serialVersionUID = 8175438641805006165L;
        private String name;
        private int age;
    }
}
