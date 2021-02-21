package cn.happyloves.rabbitmq.producers.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zc
 * @date 2020/12/10 22:15
 */
@Slf4j
@Component
public class SimpleProducers {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSimple(String queueName, int i) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Simple 直接的信息";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        map.put("i", i);
        rabbitTemplate.convertAndSend(queueName, map);
    }
}
