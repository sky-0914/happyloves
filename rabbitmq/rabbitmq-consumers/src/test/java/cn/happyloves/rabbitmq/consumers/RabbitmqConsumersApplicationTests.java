package cn.happyloves.rabbitmq.consumers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqConsumersApplicationTests {

    @Autowired
    private Consumers consumers;

    @Test
    void contextLoads() {
        consumers.send("123");
    }

}
