package cn.happyloves.redis.publish.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2020/9/16 00:02
 */
@Slf4j
@Component
public class ReceiverRedisMessage {

    public void test1Listener(String msg) {
        log.info("[1====开始消费REDIS消息队列TOPIC_TEST1数据...],消息数据[{}]", msg);
    }

    public void test2Listener(String msg) {
        log.info("[2====开始消费REDIS消息队列TOPIC_TEST2数据...],消息数据[{}]", msg);
    }

    public void test3Listener(Object msg) {
        log.info("[3====开始消费REDIS消息队列TOPIC_TEST3数据...],消息数据[{}]", msg.toString());
    }

    public void test4Listener(Object msg) {
        log.info("[4====开始消费REDIS消息队列TOPIC_TEST4数据...],消息数据[{}]", msg.toString());
    }

    public void test5Listener(Object msg) {
        log.info("[5====开始消费REDIS消息队列TOPIC_TEST5数据...],消息数据[{}]", msg.toString());
    }
}
