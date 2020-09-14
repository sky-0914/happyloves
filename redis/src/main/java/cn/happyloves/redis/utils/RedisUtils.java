package cn.happyloves.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zc
 * @date 2020/9/10 23:20
 */
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // TODO ============================================== Key相关 ==============================================

    /**
     * 获取所有key，模糊查询*key*
     * KEYS *、KEYS *key*、KEYS *key、KEYS key*
     *
     * @param key 键
     * @return 返回匹配的key集合
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 指定key缓存失效时间
     * EXPIRE key 10
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 指定key到期时间
     * EXPIREAT key 1293840000
     *
     * @param key  键
     * @param date 时间
     */
    public void expireat(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 根据key 获取过期时间
     * ttl key
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long ttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * exists key
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断多个key是否存在
     *
     * @param key 可以传一个值 或多个
     * @return 返回key存在数量
     */
    public Long hasKeys(String... key) {
        return redisTemplate.countExistingKeys(Arrays.asList(key));
    }

    /**
     * 删除缓存
     * del key1 key2 key3
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }


    // TODO ============================================== String相关 ==============================================

    /**
     * 普通缓存获取
     * GET key
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * SET key value
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     * SET key value time
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * INCR key
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 递增后的值
     */
    public Long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * DECR key
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return 递减后的值
     */
    public Long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 设置key，value
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
     * Redis分布式锁应用
     * SETNX key value time
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return 返回值
     */
    public Boolean setnx(String key, Object value, long time) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 判断当前的键的值是否为v，是的话不作操作，不实的话进行替换。如果没有这个键也不会做任何操作。
     * SETEX key time value
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return 返回值
     */
    public Boolean setex(String key, Object value, long time) {
        return redisTemplate.opsForValue().setIfPresent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * key存在设置新值，并返回旧值
     * 有key才设置新的value，并且返回上一次的value
     * GETSET key value
     *
     * @param key   键
     * @param value 值
     */
    public Object getset(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    // TODO ============================================== Hash相关 ==============================================

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public void hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     * <p>
     * HMSET key map（key1 value1 key2 value2）
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public void hmset(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * HSET key item value
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public void hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * HashGet
     * HGET key item
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * HMGET key
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * HSET key item value
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public void hset(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 删除hash表中的值
     * DELETE key item1 item2 item3
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void delete(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     * HEXISTS key item
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hexists(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * HINCRBY key item by
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 返回值
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * HDEL key item by
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return 返回值
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // TODO ============================================== Set相关 ==============================================

    /**
     * 根据key获取Set中的所有值
     * SMEMBERS key
     *
     * @param key 键
     * @return 返回值
     */
    public Set<Object> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     * SISMEMBER key value
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sismember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     * SADD key value1 value2
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sadd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetAndTime(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     * SCARD key
     *
     * @param key 键
     * @return 返回值
     */
    public Long scard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     * SREM key value1 value2
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long srem(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }
    //TODO ============================================== List相关 ==============================================

    /**
     * 将list放入缓存,从右边添加
     * RPUSH key value
     *
     * @param key   键
     * @param value 值
     */
    public void rpush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public void rpush(String key, Object value, long time) {
        redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public void rpush(String key, List<Object> value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public void rpush(String key, List<Object> value, long time) {
        redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 获取list缓存的内容
     * LRANGE key 0 -1
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return 返回值
     */
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     * LLEN key
     *
     * @param key 键
     * @return 返回值
     */
    public Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * LINDEX key index
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 返回值
     */
    public Object lindex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }


    /**
     * 根据索引修改list中的某条数据
     * LSET key index value
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public void lUpdateIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public void a() {
        redisTemplate.opsForZSet().add();
    }
}
