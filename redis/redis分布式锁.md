# Redis 分布式锁
**实现代码**
```java
/**
 * 分布式锁
 *
 * @author zc
 * @date 2020/9/15 11:17
 */
@Slf4j
@Service
public class DistributedLockService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 加锁
     *
     * @param key   键
     * @param value 值
     * @return 返回值
     */
    private Boolean lock(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, 10, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     *
     * @param key 键
     * @return 返回值
     */
    private Boolean unLock(String key) {
        return redisTemplate.delete(key);
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    public Long distributedLock(String goodsId) {
        int i = 1;
        while (true) {
            log.info(goodsId + "," + i++);
            if (this.lock(goodsId, goodsId)) {
                Long l = this.incr("incr_" + goodsId);
                log.info("递增数量：{}", l);
                this.unLock(goodsId);
                return l;
            }
        }
    }
}
```

**测试代码**
```java
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
                    log.info("DistributedLock: {}", distributedLockService.distributedLock("goodsId"));
                }
            };
            thread.start();
        }
        Thread.sleep(20000);
    }
}

```