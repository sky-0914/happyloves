package cn.happyloves.shardingsphere;

import cn.happyloves.shardingsphere.jpa.entity.Test;
import cn.happyloves.shardingsphere.jpa.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zc
 * @date 2020/9/17 17:27
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestRepository testRepository;

    @PostMapping("/save")
    public void save(@RequestBody Test test) {
        testRepository.save(test);
    }

    @GetMapping("/getAll")
    public List<Test> getAll() {
        return testRepository.findAll(Sort.by(Sort.Order.by("id")));
    }

    @GetMapping("/{id}")
    public Test getOne(@PathVariable Integer id) {
        return testRepository.findById(id).get();
    }

    @GetMapping("/getAge/{age}")
    public List<Test> getAge(@PathVariable Integer age) {
        return testRepository.getAllByAgeGreaterThanEqual(age);
    }

    @PostMapping("/")
    @Transactional(rollbackFor = Exception.class)
    public Test getAccount(@RequestBody Test test) throws Exception {
        List<Test> all = testRepository.findAll();
        System.out.println("查询所有:" + all);
        testRepository.save(test);
        if (test.getAge() < 0) {
            System.out.println("异常信息");
            throw new Exception("年龄小于0");
        } else {
            return test;
        }
    }
}
