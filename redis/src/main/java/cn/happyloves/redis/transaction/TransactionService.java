package cn.happyloves.redis.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zc
 * @date 2020/9/15 20:12
 */
@Slf4j
@Service
public class TransactionService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void test1() throws Exception {
        System.out.println(redisTemplate.opsForValue().get("test1"));
        redisTemplate.opsForValue().set("test1", "test1");
        throw new Exception("异常信息");
    }

    /**
     * 无需配置，手动开启事务
     *
     * @param flag 是否报错
     * @param key  key
     * @throws Exception 异常
     */
    public void test2(boolean flag, String key) throws Exception {
        redisTemplate.multi();

        redisTemplate.opsForValue().set(key, key);
        if (flag) {
            throw new Exception("异常信息");
        }
        redisTemplate.opsForValue().set(key + 2, key + 2);

        redisTemplate.exec();
    }
}
