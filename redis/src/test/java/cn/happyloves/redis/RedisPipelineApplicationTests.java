package cn.happyloves.redis;

import cn.happyloves.redis.pipeline.PipelineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisPipelineApplicationTests {

    @Autowired
    PipelineService pipelineService;

    /**
     * 测试管道和普通的速度
     * pipeline: 272ms
     * common: 6859ms
     */
    @Test
    void redisPipeline() {
        long t1 = System.currentTimeMillis();
        pipelineService.pipeline();
        long t2 = System.currentTimeMillis();
        pipelineService.common();
        long t3 = System.currentTimeMillis();
        System.out.println("pipeline: " + (t2 - t1) + "ms");
        System.out.println("common: " + (t3 - t2) + "ms");
    }
}
