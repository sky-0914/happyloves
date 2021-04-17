package cn.happyloves.elasticsearch.controller;

import cn.happyloves.elasticsearch.model.Account;
import cn.happyloves.elasticsearch.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zc
 * @date 2020/10/23 14:00
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("/")
    public void save(@RequestBody Account account) {
        repository.save(account);
    }

    @PostMapping("/template")
    public void saveTemplate(@RequestBody Account account) {
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(new Date());
        elasticsearchRestTemplate.save(account);
    }

    @GetMapping("/")
    public Iterable<Account> getAll() {
        return repository.findAll();
    }

    /**
     * 创建索引
     */
    @GetMapping("/createIndex")
    public void createIndex() {
        Class<?> cls = Account.class;
        //====== 方式一
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(cls);
        //判断索引是否存在
        if (indexOperations.exists()) {
            log.info("存在索引");
            indexOperations.delete();
        }
        //创建索引
        indexOperations.create();
        //创建mapping映射
        Document mapping = indexOperations.createMapping();
        indexOperations.putMapping(mapping);

        //====== 方式二，新版本已废弃
        //判断索引是否存在
        /*if (elasticsearchRestTemplate.indexExists(cls)) {
            log.info("存在索引");
            elasticsearchRestTemplate.deleteIndex(cls);
        }
        //创建索引,mapping是{}
        elasticsearchRestTemplate.createIndex(cls);
        //创建映射
        elasticsearchRestTemplate.putMapping(cls);*/
    }
}
