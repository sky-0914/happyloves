package cn.happyloves.rabbitmq.producers;

import cn.happyloves.rabbitmq.producers.direc.exchange.DirecProducers;
import cn.happyloves.rabbitmq.producers.simple.SimpleProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zc
 * @date 2020/12/16 21:14
 */
@RestController
public class SendController {
    @Autowired
    private DirecProducers direcProducers;
    @Autowired
    private SimpleProducers simpleProducers;

    @GetMapping("/simple")
    public String direc(String queueName, int i) {
        simpleProducers.sendSimple(queueName, i);
        return "ok";
    }

    @GetMapping("/direc")
    public String direc(String exchange, String routingKey, int i) {
        direcProducers.sendDirec(exchange, routingKey, i);
        return "ok";
    }

}
