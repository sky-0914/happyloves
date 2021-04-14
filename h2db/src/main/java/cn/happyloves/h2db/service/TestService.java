package cn.happyloves.h2db.service;

import cn.happyloves.h2db.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ZC
 * @date 2021/3/1 21:14
 */
@Slf4j
@Service
public class TestService {

    @Autowired
    private AccountService accountService;


    public TestService() {
        System.out.println("TestService-构造方法");
    }

    public Object test(String str0, int count, Map m, List l, Object[] os) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TestService=====》》》》》test()");
        return os[0];
    }

}
