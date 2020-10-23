package cn.happyloves.elasticsearch.controller;

import cn.happyloves.elasticsearch.model.Student;
import cn.happyloves.elasticsearch.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zc
 * @date 2020/10/23 14:00
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @PostMapping("/")
    public void save(@RequestBody Student student) {
        repository.save(student);
    }

    @GetMapping("/")
    public Iterable<Student> getAll() {
        return repository.findAll();
    }

}
