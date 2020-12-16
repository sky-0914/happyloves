package cn.happyloves.rabbitmq.consumers.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author zc
 * @date 2020/12/15 19:07
 */
@Slf4j
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.debug("确认回调:");
        try {
            String messageStr = new ObjectMapper().writeValueAsString(correlationData);
            System.out.println(messageStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(b);
        System.out.println(s);
    }
}
