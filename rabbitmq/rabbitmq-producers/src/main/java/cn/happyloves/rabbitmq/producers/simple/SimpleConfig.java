package cn.happyloves.rabbitmq.producers.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2020/12/17 00:27
 */
@Configuration
public class SimpleConfig {

    public static final String SIMPLE_QUEUE_ONE_TO_ONE = "SIMPLE_QUEUE_ONE_TO_ONE";
    public static final String SIMPLE_QUEUE_ONE_TO_MANY = "SIMPLE_QUEUE_ONE_TO_MANY";

    @Bean
    public Queue simpleQueueOne() {
        return new Queue(SIMPLE_QUEUE_ONE_TO_ONE);
    }

    @Bean
    public Queue simpleQueueMany() {
        return new Queue(SIMPLE_QUEUE_ONE_TO_MANY);
    }
}
