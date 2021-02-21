package cn.happyloves.rabbitmq.producers.config.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author zc
 * @date 2020/12/15 19:07
 * 不能定义多个确认机制，不然报错：Only one ConfirmCallback is supported by each RabbitTemplate
 * correlationData：队列唯一标识, ack：消息投递状态, cause:异常信息。
 * 确认机制-触发机制：
 * 1.当找不到交换机 ack: false
 * 2.交换机和队列啥都没找到触发  ack: false
 * 3.找到交换机了，但是没找到队列 ack: true;这种情况触发的是 ConfirmCallback和ReturnCallback两个回调函数。
 * 4.发送成功 ack: true
 */
@Slf4j
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
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
    }
}
