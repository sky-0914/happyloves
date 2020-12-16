package cn.happyloves.rabbitmq.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2020/12/10 22:06
 */
@Configuration
public class Config {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //生产者接收回调消息  correlationData：队列唯一标识, ack：消息投递状态, cause:异常信息
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
        rabbitTemplate.setRecoveryCallback((var1) -> {
            System.out.println("重试回调:");
            String messageStr = new ObjectMapper().writeValueAsString(var1);
            System.out.println(messageStr);
            return var1;
        });
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
        return rabbitTemplate;
    }

}
