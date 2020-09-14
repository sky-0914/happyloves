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
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    RedisUtils redisUtils;

    /**
     * Redis Key相关操作
     */
    @Test
    void redisKey() {
        //设置缓存
        redisUtils.set("abc", "abc");
        redisUtils.set("aaa", "aaa");
        redisUtils.set("bbb", "bbb");
        redisUtils.set("ccc", "ccc");
        //获取key和模糊查询key
        System.out.println(redisUtils.keys("abc"));
        System.out.println(redisUtils.keys("*a*"));
        System.out.println(redisUtils.keys("*b*"));

        Student s1 = Student.builder()
                .name("张三")
                .age(18)
                .build();
        Student s2 = Student.builder()
                .name("李四")
                .age(19)
                .build();
        //设置缓存
        redisUtils.set("s1", s1);
        System.out.println(redisUtils.get("s1"));
        redisUtils.set("s2", s2);
        System.out.println(redisUtils.get("s2"));
        //模糊查询key
        System.out.println(redisUtils.keys("s*"));
        //获取指定key的value
        System.out.println(redisUtils.get("s1"));
        //设置指定key的缓存时间
        redisUtils.expire("s1", 10);
        //获取指定key的剩余缓存时间
        System.out.println(redisUtils.getExpire("s1"));
        //判断key是否存在
        System.out.println(redisUtils.hasKey("s1"));
        //判断多个key是否存在
        System.out.println(redisUtils.hasKeys("s1", "s2"));
        //删除一个或多个key缓存
        redisUtils.del("s1", "s2");
    }

    @Test
    void redisString() {
        System.out.println("递增：" + redisUtils.incr("aaa", 10));
        System.out.println("递减：" + redisUtils.decr("aaa", 10));

        System.out.println("没有key才设置：" + redisUtils.setnx("aaa", "aaa", 100));
        System.out.println("没有key才设置：" + redisUtils.setnx("aaa", "aaa", 100));

        redisUtils.set("bbb", "aaa");
        System.out.println("有key才设置：" + redisUtils.setex("bbb", "bbb", 100));
        System.out.println("有key才设置：" + redisUtils.setex("bbb", "bbb", 100));
    }

    @Test
    void redisHash() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 18);

        redisUtils.hmset("aaa", map, 100);
        System.out.println("获取缓存Key的Map字段值：" + redisUtils.hget("aaa", "name"));
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
