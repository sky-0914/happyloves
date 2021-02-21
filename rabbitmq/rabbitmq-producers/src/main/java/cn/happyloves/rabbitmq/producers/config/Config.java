package cn.happyloves.rabbitmq.producers.config;

import cn.happyloves.rabbitmq.producers.config.callback.MyConfirmCallback;
import cn.happyloves.rabbitmq.producers.config.callback.MyRecoveryCallback;
import cn.happyloves.rabbitmq.producers.config.callback.MyReturnCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2020/12/10 22:06
 */
@Slf4j
@Configuration
public class Config {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 前提是publisher-returns需要先开启，此种方式在yml配置中定义spring.rabbit.template.mandatory=true 无效,必须在配置中rabbitTemplate.setMandatory(true);才生效
        rabbitTemplate.setMandatory(true);

        //方式一：通过实现接口方法
//        rabbitTemplate.setConfirmCallback(new MyConfirmCallback());
//        rabbitTemplate.setReturnCallback(new MyReturnCallback());
//        rabbitTemplate.setRecoveryCallback(new MyRecoveryCallback());

        //方式二：通过Lambda语法实现
        // 不能定义多个确认机制，不然报错：Only one ConfirmCallback is supported by each RabbitTemplate
        //correlationData：队列唯一标识, ack：消息投递状态, cause:异常信息。
        // 确认机制-触发机制：
        // 1.当找不到交换机
        // 2.交换机和队列啥都没找到触发
        // 3.找到交换机了，但是没找到队列
        // 4.发送成功
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            try {
                String messageStr = new ObjectMapper().writeValueAsString(correlationData);
                if (ack) {
                    log.info("确认回调[成功] - correlationData: {},ack: {},cause: {}", messageStr, ack, cause);
                } else {
                    log.error("确认回调[失败] - correlationData: {},ack: {},cause: {}", messageStr, ack, cause);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        //失败回调方法-触发机制：找到交换机了，但是没找到队列
        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            try {
                String messageStr = new ObjectMapper().writeValueAsString(message);
                log.error("返回回调 - message: {},replyCode: {},replyText: {},exchange: {},routingKey: {}", messageStr, replyCode, replyText, exchange, routingKey);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        rabbitTemplate.setRecoveryCallback((var1) -> {
            String messageStr = new ObjectMapper().writeValueAsString(var1);
            log.info("重试回调 - message: {}", messageStr);
            return var1;
        });
        return rabbitTemplate;
    }

}
