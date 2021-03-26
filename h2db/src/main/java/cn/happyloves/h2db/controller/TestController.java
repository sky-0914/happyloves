package cn.happyloves.h2db.controller;

import cn.happyloves.h2db.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

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
    public void test() {
        TestService bean = context.getBean(TestService.class);
        System.out.println(bean);
        Object test = context.getBean("testService");
        System.out.println(test);
        System.out.println("===========================");
        Object test1 = factory.getBean("testService");
        System.out.println(test1);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> voidCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            log.info("voidCompletableFuture1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "voidCompletableFuture1";
        });
        CompletableFuture<String> voidCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            log.info("bbbb--->");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("lalala!");
        });

        log.info("123");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("456");
//        CompletableFuture.anyOf(voidCompletableFuture1, voidCompletableFuture2).join();
        String s = voidCompletableFuture1.get();
        log.info("voidCompletableFuture1===>>>{}",s);
        String s1 = voidCompletableFuture2.get();
        log.info("voidCompletableFuture2===>>>{}",s1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("789");
    }
}
