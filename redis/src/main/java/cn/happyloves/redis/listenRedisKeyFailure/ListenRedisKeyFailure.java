package cn.happyloves.redis.listenRedisKeyFailure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

/**
 * Redis监听缓存Key失效通知
 *
 * @author zc
 * @date 2020/9/15 15:12
 */
@Slf4j
@Component
public class ListenRedisKeyFailure extends KeyExpirationEventMessageListener {
    public ListenRedisKeyFailure(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 配置监听哪个频道
     * 我们在配置文件中配置的是8数据库，所以监听8数据库
     */
    private static final Topic KEYEVENT_EXPIRED_TOPIC = new PatternTopic("__keyevent@8__:expired");

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        // 频道可以是多，多个传list
        listenerContainer.addMessageListener(this, KEYEVENT_EXPIRED_TOPIC);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * message：Redis失效的Key，无法获取value值
     *
     * @param message 失效key
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        if (expiredKey.startsWith("test:")) {
            //如果是Order:开头的key，进行处理
            log.info("Redis 过期的 Key：{}", expiredKey);
        }
    }
}
