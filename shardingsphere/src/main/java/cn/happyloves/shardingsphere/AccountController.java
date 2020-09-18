package cn.happyloves.shardingsphere;

import cn.happyloves.shardingsphere.jpa.entity.Account;
import cn.happyloves.shardingsphere.jpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zc
 * @date 2020/9/17 17:27
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/save")
    public void save(@RequestBody Account account) {
        accountRepository.save(account);
    }

    @GetMapping("/getAll")
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @PostMapping("/")
    @Transactional(rollbackFor = Exception.class)
    public Account getAccount(@RequestBody Account account) throws Exception {
        List<Account> all = accountRepository.findAll();
        System.out.println("查询所有:" + all);
        accountRepository.save(account);
        if (account.getAge() < 0) {
            System.out.println("异常信息");
            throw new Exception("年龄小于0");
        } else {
            return account;
        }
    }
}
