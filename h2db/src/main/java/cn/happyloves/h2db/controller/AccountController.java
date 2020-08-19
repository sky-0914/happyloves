package cn.happyloves.h2db.controller;

import cn.happyloves.h2db.entity.Account;
import cn.happyloves.h2db.jpa.AccountJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zc
 * @date 2020/8/19 17:04
 * 采用RESTFUL接口风格
 */
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
}
