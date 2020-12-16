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

        //correlationData：队列唯一标识, ack：消息投递状态, cause:异常信息。
        // 触发机制：
        // 1.当找不到交换机
        // 2.交换机和队列啥都没找到触发
        // 3.找到交换机了，但是没找到队列
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("确认回调:");
            try {
                String messageStr = new ObjectMapper().writeValueAsString(correlationData);
                System.out.println(messageStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(ack);
            System.out.println(cause);
        });
        //触发机制：找到交换机了，但是没找到队列
        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            System.out.println("返回回调:");
            try {
                String messageStr = new ObjectMapper().writeValueAsString(message);
                System.out.println(messageStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(message);
            System.out.println(replyCode);
            System.out.println(exchange);
            System.out.println(routingKey);
        });
        rabbitTemplate.setRecoveryCallback((var1) -> {
            System.out.println("重试回调:");
            String messageStr = new ObjectMapper().writeValueAsString(var1);
            System.out.println(messageStr);
            return var1;
        });


        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(exchangeStr, routingKeyStr, map);
    }
}
