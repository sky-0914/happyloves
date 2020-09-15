package cn.happyloves.redis;

import cn.happyloves.redis.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTransactionApplicationTests {

    @Autowired
    TransactionService transactionService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void redisKey() throws Exception {
//        transactionService.test1();
//        transactionService.test2(false, "key1");
        transactionService.test2(true, "key2");

//        System.out.println("test1: " + redisTemplate.opsForValue().get("test1"));
//        System.out.println("key11: " + redisTemplate.opsForValue().get("key11"));
//        System.out.println("key12: " + redisTemplate.opsForValue().get("key12"));
//
//        System.out.println("key21: " + redisTemplate.opsForValue().get("key21"));
//        System.out.println("key22: " + redisTemplate.opsForValue().get("key22"));
    }
}
