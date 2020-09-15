//package cn.happyloves.redis.listenRedisKeyFailure;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//
///**
// * @author zc
// * @date 2020/9/16 00:18
// */
//@Configuration
//public class ListenBeanConfig {
//
//    /**
//     * 配置Redis监听类
//     * 与Spring Redis Session 冲突
//     * springSessionRedisMessageListenerContainer: defined by method 'springSessionRedisMessageListenerContainer' in class path resource [org/springframework/boot/autoconfigure/session/RedisSessionConfiguration$SpringBootRedisHttpSessionConfiguration.class]
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RedisMessageListenerContainer listenRedisKeyFailureContainer(RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
////        container.addMessageListener(new RedisExpiredListener(), new PatternTopic("__keyevent@0__:expired"));
//        return container;
//    }
//}
