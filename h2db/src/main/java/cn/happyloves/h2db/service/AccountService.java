package cn.happyloves.h2db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/3/1 21:14
 */
@Service
public class AccountService{
    @Autowired
    private TestService testService;

    public AccountService() {
        System.out.println("Account-构造方法");
    }

    public void test() {
        System.out.println("AccountService=====》》》》》test()");
    }

}
