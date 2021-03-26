package cn.happyloves.h2db.controller;

import cn.happyloves.h2db.entity.Account;
import cn.happyloves.h2db.jpa.AccountJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zc
 * @date 2020/8/19 17:04
 * 采用RESTFUL接口风格
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountJPA accountJPA;

    /**
     * 保存账号信息
     *
     * @param account 请求值
     */
    @PostMapping("/")
    public void save(@RequestBody Account account) {
        accountJPA.save(account);
    }

    /**
     * 返回所有账号信息
     *
     * @return 返回值
     */
    @GetMapping("/")
    public List<Account> getAll() {
        return accountJPA.findAll();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("开始");
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("stringCompletableFuture===返回啦");
            return "==== CompletableFuture ====";
        });
        log.info("123");
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.info("456");
//        log.info("获取值：{}", stringCompletableFuture.get());
        log.info("结束");
    }
}
