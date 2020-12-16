package cn.happyloves.rabbitmq.producers;

import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2020/12/10 22:06
 * 方式一：定义createRabbitTemplate
 */
@Configuration
public class Config {

//    @Bean
//    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        // 此种方式在yml配置中定义spring.rabbit.template.mandatory=true 无效,必须在配置中rabbitTemplate.setMandatory(true);才生效
//        rabbitTemplate.setMandatory(true);
//        //correlationData：队列唯一标识, ack：消息投递状态, cause:异常信息。触发机制：当找不到交换机触发
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            System.out.println("确认回调:");
//            try {
//                String messageStr = new ObjectMapper().writeValueAsString(correlationData);
//                System.out.println(messageStr);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            System.out.println(ack);
//            System.out.println(cause);
//        });
//        rabbitTemplate.setRecoveryCallback((var1) -> {
//            System.out.println("重试回调:");
//            String messageStr = new ObjectMapper().writeValueAsString(var1);
//            System.out.println(messageStr);
//            return var1;
//        });
//        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
//            System.out.println("返回回调:");
//            try {
//                String messageStr = new ObjectMapper().writeValueAsString(message);
//                System.out.println(messageStr);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            System.out.println(message);
//            System.out.println(replyCode);
//            System.out.println(exchange);
//            System.out.println(routingKey);
//        });
//        return rabbitTemplate;
//    }

}
