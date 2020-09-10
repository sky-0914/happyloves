package cn.happyloves.example.guavacache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author zc
 * @date 2020/9/9 12:44
 */
public class GuavaCache {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, Object> commonCache = CacheBuilder.newBuilder()
                //设置缓存容器的初始化容量为10（可以存10个键值对）
                .initialCapacity(10)
                //最大缓存容量是100,超过100后会安装LRU策略-最近最少使用，具体百度-移除缓存项
                .maximumSize(100)
                //设置写入缓存后1分钟后过期
                .expireAfterWrite(10, TimeUnit.SECONDS).build();

        //放入缓存
        commonCache.put("a", "a");
        commonCache.put("a", "b");

        for (int i = 0; i < 11; i++) {
            Thread.sleep(1000);
            //取出缓存
            System.out.println(commonCache.getIfPresent("a"));
        }
    }
}
