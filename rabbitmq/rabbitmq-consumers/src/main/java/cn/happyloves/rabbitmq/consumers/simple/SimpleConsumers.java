package cn.happyloves.rabbitmq.consumers.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zc
 * @date 2020/12/17 00:35
 */
@Slf4j
@Component
public class SimpleConsumers {

    /**
     * 一对一，消费者
     *
     * @param map 消息
     */
    @RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_ONE_TO_ONE)
    public void handlerOne(Map map) {
        log.info("处理器：一对一， Simple 消费者===>>>> {}", map);
    }

    /**
     * 一对多，消费者0
     *
     * @param map 消息
     */
    @RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_ONE_TO_MANY)
    public void handlerMany0(Map map) {
        log.info("处理器：一对多-0， - Simple 消费者===>>>> {}", map);
    }

    /**
     * 一对多，消费者1
     *
     * @param map 消息
     */
    @RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_ONE_TO_MANY)
    public void handlerMany1(Map map) {
        log.info("处理器：一对多-1， - Simple 消费者===>>>> {}", map);
    }
}
