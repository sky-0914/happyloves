package cn.happyloves.rabbitmq.producers.direc.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2020/12/16 20:59
 * Direct Exchange
 * <p>
 * 直连型交换机，根据消息携带的路由键将消息投递给对应队列。
 * <p>
 * 大致流程，有一个队列绑定到一个直连交换机上，同时赋予一个路由键 routing key 。
 * 然后当一个消息携带着路由值为X，这个消息通过生产者发送给交换机时，交换机就会根据这个路由值X去寻找绑定值也是X的队列。
 */
@Configuration
public class DirectExchangeConfig {
    public static final String DIRECT_EXCHANGE_TEST_1 = "directExchangeTest1";
    public static final String DIRECT_EXCHANGE_TEST_2 = "directExchangeTest2";

    public static final String QUEUE_1 = "queue.1";
    public static final String QUEUE_2 = "queue.2";

    public static final String ROUTING_KEY_1 = "ROUTING_KEY_1";
    public static final String ROUTING_KEY_2 = "ROUTING_KEY_2";


    /**
     * Direct交换机
     *
     * @return 交换机
     */
    @Bean
    DirectExchange directExchangeTest1() {
        //  return new DirectExchange(DIRECT_EXCHANGE_TEST_1,true,true);
        return new DirectExchange(DIRECT_EXCHANGE_TEST_1);
    }

    @Bean
    DirectExchange directExchangeTest2() {
        //  return new DirectExchange(DIRECT_EXCHANGE_TEST_1,true,true);
        return new DirectExchange(DIRECT_EXCHANGE_TEST_2);
    }

    /**
     * 队列
     *
     * @return 队列
     */
    @Bean
    public Queue directQueueTest1() {
        // durable:是否持久化,默认是true,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，默认是false，当没有生产者或者消费者使用此队列，该队列会自动删除
        return new Queue(QUEUE_1);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键：routingKey
     *
     * @return 绑定信息
     */
    @Bean
    Binding bindingDirect1() {
        return BindingBuilder.bind(directQueueTest1()).to(directExchangeTest1()).with(ROUTING_KEY_1);
    }

    @Bean
    Binding bindingDirect2() {
        return BindingBuilder.bind(directQueueTest1()).to(directExchangeTest1()).with(ROUTING_KEY_2);
    }

}
