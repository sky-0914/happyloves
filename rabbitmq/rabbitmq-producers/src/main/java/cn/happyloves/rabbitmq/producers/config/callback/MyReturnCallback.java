package cn.happyloves.rabbitmq.producers.config.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author zc
 * @date 2020/12/15 19:08
 * 失败回调方法-触发机制：找到交换机了，但是没找到队列
 */
@Slf4j
public class MyReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            String messageStr = new ObjectMapper().writeValueAsString(message);
            log.error("返回回调 - message: {},replyCode: {},replyText: {},exchange: {},routingKey: {}", messageStr, replyCode, replyText, exchange, routingKey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
