package cn.happyloves.redis.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 管道，批量操作、Pipeline
 *
 * @author zc
 * @date 2020/9/15 18:46
 */
@Service
public class PipelineService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void pipeline() {
        redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            for (int i = 0; i < 100; i++) {
                connection.set(("pipeline:" + i).getBytes(), "123".getBytes());
            }
            return null;
        });
    }

    public void common() {
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().set("common:" + i, "123");
        }
    }
}
