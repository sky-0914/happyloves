package cn.happyloves.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisListenKeyFailureApplicationTests {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void redisKey() throws InterruptedException {
        //设置缓存，2秒过期
        redisTemplate.opsForValue().set("test:1", "", 2, TimeUnit.SECONDS);
        //程序等待5秒
        Thread.sleep(50000);
    }
}
