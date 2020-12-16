package cn.happyloves.rabbitmq.consumers.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author zc
 * @date 2020/12/15 19:08
 */
@Slf4j
public class MyReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.debug("返回回调:");
        try {
            String messageStr = new ObjectMapper().writeValueAsString(message);
            System.out.println(messageStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
//        System.out.println(replyCode);
//        System.out.println(exchange);
//        System.out.println(routingKey);
    }
}
