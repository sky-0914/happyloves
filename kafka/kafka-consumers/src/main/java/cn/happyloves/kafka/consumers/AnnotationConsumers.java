package cn.happyloves.kafka.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ZC
 * @date 2021/4/16 17:48
 */
@Slf4j
@Component
public class AnnotationConsumers {

    public static final String MY_TOPIC = "example";

    @KafkaListener(topics = {MY_TOPIC})
    public void consume(String message) {
        log.info("receive msg: {}" + message);
    }
}
