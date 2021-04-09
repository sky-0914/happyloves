package cn.happyloves.netty.rpc.examplex.server.service;

import cn.happyloves.netty.rpc.examples.api.Test1Api;
import cn.happyloves.rpc.client.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/3/2 23:59
 */
@Slf4j
@Service
@RpcServer
public class TestServiceImpl implements Test1Api {
    @Override
    public void test() {
        log.info("111111111");
    }

    @Override
    public void test(int id, String name) {
        log.info("222222222,{},{}", id, name);
    }

    @Override
    public String testStr(int id) {
        log.info("33333333333333333,{}", id);
        return "33333333333333333 " + id;
    }

    @Override
    public Object testObj() {
        log.info("444444444444444444");
        Account account = new Account();
        account.setName("张三");
        return account;
    }
}
