package cn.happyloves.redis;

import cn.happyloves.redis.distributedlock.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class RedisDistributedLockServiceApplicationTests {

    @Autowired
    DistributedLockService distributedLockService;

    @Test
    void redisDistributedLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int a = i;
            Thread thread = new Thread("lock") {
                @Override
                public void run() {
                    log.info("aaa: {}", distributedLockService.distributedLock("goodsId"));
                }
            };
            thread.start();
        }
        Thread.sleep(20000);
    }
}
