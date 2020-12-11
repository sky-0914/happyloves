package cn.happyloves.rabbitmq.producers;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2020/12/11 16:18
 */
@Slf4j
@Component
public class Producers {

    /**
     * 监听 queue-rabbit-springboot-advance 队列
     *
     * @param obj     接收到的消息
     * @param message
     * @param channel
     */
    @RabbitListener(queues = Constant.QUEUE)
    public void receiveMessage(Object obj, Message message, Channel channel) {
        log.info("消费者===>{},{},{}", obj.toString(), message, channel);
    }
}
