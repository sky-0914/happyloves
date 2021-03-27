package cn.happyloves.h2db.controller;

import cn.happyloves.h2db.service.AccountService;
import cn.happyloves.h2db.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zc
 * @date 2020/8/19 17:04
 * 采用RESTFUL接口风格
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private BeanFactory factory;


    @GetMapping("/")
    public void a() {
        TestService bean = context.getBean(TestService.class);
        System.out.println(bean);
        Object test = context.getBean("testService");
        System.out.println(test);
        System.out.println("===========================");
        Object test1 = factory.getBean("testService");
        System.out.println(test1);
    }

    @Autowired
    private TestService testService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/test")
    public void test() {
        testService.test();
        accountService.test();
    }
}
