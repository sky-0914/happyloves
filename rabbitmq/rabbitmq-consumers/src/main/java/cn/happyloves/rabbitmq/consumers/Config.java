package cn.happyloves.rabbitmq.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
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

    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(() -> {

        });
        rabbitTemplate.setRecoveryCallback();
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

    class A implements RabbitTemplate.ReturnCallback {

        @Override
        public void returnedMessage(Message message, int i, String s, String s1, String s2) {

        }
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    DirectExchange testDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        return new DirectExchange("TestDirectExchange", true, false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirectRouting");
    }

    @Bean
    public Queue testDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除
        return new Queue(Constant.QUEUE);
    }
}
