package cn.happyloves.redis.publish.subscribe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author zc
 * @date 2020/9/16 00:02
 */
@Configuration
public class MqBeanConfig {


    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param connectionFactory
     * @param test1ListenerAdapter
     * @param test2ListenerAdapter
     * @param test3ListenerAdapter
     * @param test4ListenerAdapter
     * @return
     */
    @Bean("container")
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter test1ListenerAdapter,
                                            MessageListenerAdapter test2ListenerAdapter,
                                            MessageListenerAdapter test3ListenerAdapter,
                                            MessageListenerAdapter test4ListenerAdapter,
                                            MessageListenerAdapter test5ListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //监听TEST1情况主题并绑定消息订阅处理器
        container.addMessageListener(test1ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST1));
        //监听TEST2主题并绑定消息订阅处理器
        container.addMessageListener(test2ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST2));
        //监听TEST3主题并绑定消息订阅处理器
        container.addMessageListener(test3ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST3));
        //监听TEST4主题并绑定消息订阅处理器
        container.addMessageListener(test4ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST4));
        //监听TEST5主题并绑定消息订阅处理器,监听器同时订阅多个主题
        container.addMessageListener(test5ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST1));
        container.addMessageListener(test5ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST2));
        container.addMessageListener(test5ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST3));
        container.addMessageListener(test5ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST4));
        return container;
    }

    @Bean
    MessageListenerAdapter test1ListenerAdapter(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "test1Listener");
    }

    @Bean
    MessageListenerAdapter test2ListenerAdapter(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "test2Listener");
    }

    @Bean
    MessageListenerAdapter test3ListenerAdapter(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "test3Listener");
    }

    @Bean
    MessageListenerAdapter test4ListenerAdapter(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "test4Listener");
    }

    @Bean
    MessageListenerAdapter test5ListenerAdapter(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "test5Listener");
    }
}
