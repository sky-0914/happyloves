package cn.happyloves.rabbitmq.consumers.direc.exchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zc
 * @date 2020/12/16 21:11
 * //监听的队列名称 @RabbitListener(queues = {DirectExchangeConfig.QUEUE_1, DirectExchangeConfig.QUEUE_2})
 */
@Slf4j
public class DirectHandler {

//    @Component
//    @RabbitListener(queues = {DirectExchangeConfig.QUEUE_1})
//    static class DirectHandler1 {
//        @RabbitHandler
//        public void handler(Map map) {
//            log.info("路由Key:{} ,DirectReceiver消费者收到消息  : {}", DirectExchangeConfig.QUEUE_1, map);
//        }
//    }

    @Component
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = DirectExchangeConfig.DIRECT_EXCHANGE_TEST_1),
            value = @Queue(value = DirectExchangeConfig.QUEUE_1),
            key = {DirectExchangeConfig.ROUTING_KEY_1}
    ))
    static class DirectHandler2 {
        @RabbitHandler
        public void handler(Map map) {
            int i = (int) map.get("i");
            log.info("路由Key:{} ,DirectReceiver消费者收到消息  : {},{}", DirectExchangeConfig.QUEUE_1, map, 1 / i);
        }
    }

    @Component
    static class DirectHandler3 {
        @RabbitListener(bindings = @QueueBinding(
                exchange = @Exchange(value = DirectExchangeConfig.DIRECT_EXCHANGE_TEST_1),
                value = @Queue(value = DirectExchangeConfig.QUEUE_1),
                key = {DirectExchangeConfig.ROUTING_KEY_2}
        ))
        public void handler(Map map) {
            int i = (int) map.get("i");
            log.info("路由Key:{} ,DirectReceiver消费者收到消息  : {},{}", DirectExchangeConfig.QUEUE_2, map, 1 / i);
        }
    }

}
