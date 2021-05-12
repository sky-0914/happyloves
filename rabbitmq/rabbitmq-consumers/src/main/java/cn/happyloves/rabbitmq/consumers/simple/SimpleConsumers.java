package cn.happyloves.rabbitmq.consumers.simple;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author zc
 * @date 2020/12/17 00:35
 */
@Slf4j
@Component
public class SimpleConsumers {

    /**
     * 一对一，消费者
     *
     * @param map 消息
     */
    @RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_ONE_TO_ONE)
    public void handlerOne(Map map, Message message, Channel channel) throws IOException {
        log.info("处理器：一对一， Simple 消费者===>>>> map: {},message: {},channel: {}", map, message, channel);
        int i = (int) map.get("i");
        try {
            System.out.println(10 / i);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                System.out.println(message.getMessageProperties().getRedelivered());
                log.error("消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息，与basicNack区别在于不能进行批量操作，其他用法很相似。
                //参数一（deliveryTag）：表示消息投递序号。
                //参数二（requeue）：值为 true 消息将重新入队列。
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息即将再次返回队列处理...");
                //表示失败确认，一般在消费消息业务异常时用到此方法，可以将消息重新投递入队列。
                //参数一（deliveryTag）：表示消息投递序号。
                //参数二（multiple）：是否批量确认。
                //参数三（requeue）：值为 true 消息将重新入队列。(假设我先发送三条消息deliveryTag分别是5、6、7，可它们都没有被确认，当我发第四条消息此时deliveryTag为8，multiple设置为 true，会将5、6、7、8的消息全部进行确认。)
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
        log.info("消息正常处理...");
        //表示成功确认，使用此回执方法后，消息会被rabbitmq broker 删除。
        //参数一（deliveryTag）：表示消息投递序号，每次消费消息或者消息重新投递后，deliveryTag都会增加。手动消息确认模式下，我们可以对指定deliveryTag的消息进行ack、nack、reject等操作。
        //参数二（multiple）：是否批量确认，值为 true 则会一次性 ack所有小于当前消息 deliveryTag 的消息。
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 一对多，消费者0
     *
     * @param map 消息
     */
    @RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_ONE_TO_MANY)
    public void handlerMany0(Map map, Message message, Channel channel) throws IOException {
        log.info("处理器：一对多-0， - Simple 消费者===>>>> {}", map);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 一对多，消费者1
     *
     * @param map 消息
     */
    @RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_ONE_TO_MANY)
    public void handlerMany1(Map map, Message message, Channel channel) throws IOException {
        log.info("处理器：一对多-1， - Simple 消费者===>>>> {}", map);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
