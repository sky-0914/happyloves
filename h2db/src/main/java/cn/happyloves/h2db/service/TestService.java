package cn.happyloves.h2db.service;

import cn.happyloves.h2db.entity.Account;
import cn.happyloves.rpc.api.Test1Api;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/3/1 21:14
 */
@Service
public class TestService implements Test1Api {
    public void test11() {
        System.out.println("TEST");
    }

    @Override
    public void test() {
        System.out.println("111111111");
    }

    @Override
    public void test(int id, String name) {
        System.out.println("222222222 " + id + " " + name);
    }

    @Override
    public String testStr(int id) {
        System.out.println("33333333333333333 " + id);
        return "33333333333333333 " + id;
    }

    @Override
    public Object testObj() {
        System.out.println("444444444444444444");
        Account account = new Account();
        account.setName("张三");
        return account;
    }

}
