package cn.happyloves.rabbitmq.consumers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2020/12/10 22:15
 */
@Component
public class Consumers {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object obj) {
        rabbitTemplate.convertAndSend(Constant.QUEUE, obj);
    }
}
