package cn.happyloves.redis;

import cn.happyloves.redis.cache.CacheService;
import cn.happyloves.redis.cache.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisCacheApplicationTests {

    @Autowired
    CacheService cacheService;

    /**
     * 查询数据
     * 2020-09-14 20:50:10.650  INFO 57271 --- [           main] cn.happyloves.redis.cache.CacheService   : 查询数据
     * [StudentDTO(id=1, name=张三, age=18), StudentDTO(id=2, name=李四, age=20)]
     * 2020-09-14 20:50:10.819  INFO 57271 --- [           main] cn.happyloves.redis.cache.CacheService   : 查询单条数据
     * [StudentDTO(id=1, name=张三, age=18)]
     * 2020-09-14 20:50:10.886  INFO 57271 --- [           main] cn.happyloves.redis.cache.CacheService   : 查询单条数据
     * [StudentDTO(id=2, name=李四, age=20)]
     * [StudentDTO(id=1, name=张三, age=18), StudentDTO(id=2, name=李四, age=20)]
     * [StudentDTO(id=1, name=张三, age=18)]
     * [StudentDTO(id=2, name=李四, age=20)]
     * 2020-09-14 20:50:11.118  INFO 57271 --- [           main] cn.happyloves.redis.cache.CacheService   : 保存数据
     * [StudentDTO(id=1, name=张三, age=18), StudentDTO(id=2, name=李四, age=22)]
     * 2020-09-14 20:50:11.205  INFO 57271 --- [           main] cn.happyloves.redis.cache.CacheService   : 查询单条数据
     * [StudentDTO(id=2, name=李四, age=22)]
     */
    @Test
    void redisCache() {
        System.out.println(cacheService.getAll());
        System.out.println(cacheService.getOne(1L));
        System.out.println(cacheService.getOne(2L));

        System.out.println(cacheService.getAll());
        System.out.println(cacheService.getOne(1L));
        System.out.println(cacheService.getOne(2L));


        System.out.println(cacheService.save(StudentDTO.builder()
                .id(2L)
                .age(22)
                .name("李四")
                .build()));
        System.out.println(cacheService.getOne(2L));
    }
}
