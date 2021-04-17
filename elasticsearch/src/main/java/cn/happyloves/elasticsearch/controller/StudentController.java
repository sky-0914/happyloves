package cn.happyloves.elasticsearch.controller;

import cn.happyloves.elasticsearch.model.Student;
import cn.happyloves.elasticsearch.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zc
 * @date 2020/10/23 14:00
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentRepository repository;

    @PostMapping("/")
    public void save(@RequestBody Student student) {
        repository.save(student);
    }

    @GetMapping("/")
    public Iterable<Student> getAll() {
        return repository.findAll();
    }

}
