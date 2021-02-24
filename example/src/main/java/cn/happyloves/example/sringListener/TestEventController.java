package cn.happyloves.example.sringListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZC
 * @date 2021/2/24 21:35
 */
@RestController
@RequestMapping("/testEvent")
public class TestEventController {
    @Autowired
    private ApplicationContext act;

    //发布事件
    @GetMapping("/pubEvent")
    public void pubEvent() {
        //TODO:业务处理
        System.out.println("发布事件");
        act.publishEvent(new TestEvent("发送邮件"));
        //TODO:业务处理
    }

}
