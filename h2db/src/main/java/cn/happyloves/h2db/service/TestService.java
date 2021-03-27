package cn.happyloves.h2db.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void test() {
        System.out.println("TestService=====》》》》》test()");
    }
}
