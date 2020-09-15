package cn.happyloves.redis;

import cn.happyloves.redis.publish.subscribe.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisMqApplicationTests {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void redisMq() {
        redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST1, "111111");
        redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST2, "222222");
        TestVO tvo1 = new TestVO();
        tvo1.setName("张三");
        redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST3, tvo1);
        TestVO tvo2 = new TestVO();
        tvo2.setName("李四");
        redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST4, tvo2);
    }
}
