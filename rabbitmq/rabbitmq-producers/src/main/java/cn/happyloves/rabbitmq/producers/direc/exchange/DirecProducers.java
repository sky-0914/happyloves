package cn.happyloves.rabbitmq.producers.direc.exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
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
@Component
public class DirecProducers {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDirec(String exchangeStr, String routingKeyStr, int i) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Direct Exchange 直接的信息";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        map.put("i", i);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(exchangeStr, routingKeyStr, map);
    }
}
