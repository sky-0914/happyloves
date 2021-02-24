package cn.happyloves.example.sringListener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author ZC
 * @date 2021/2/24 21:33
 */
@Component
public class TestEventListener implements ApplicationListener<TestEvent> {
    @Override
    public void onApplicationEvent(TestEvent testEvent) {
        System.out.println("监听事件 ===== 》》》 " + testEvent.getSource());
    }
}
