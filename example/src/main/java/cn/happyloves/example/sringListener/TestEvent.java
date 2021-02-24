package cn.happyloves.example.sringListener;

import org.springframework.context.ApplicationEvent;

/**
 * @author ZC
 * @date 2021/2/24 21:32
 */
public class TestEvent extends ApplicationEvent {
    public TestEvent(String source) {
        super(source);
    }
}
