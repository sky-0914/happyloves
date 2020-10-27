package cn.happyloves.elasticsearch.repository;

import cn.happyloves.elasticsearch.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zc
 * @date 2020/10/23 14:00
 */
public interface StudentRepository extends ElasticsearchRepository<Student, Long> {

    /**
     * 查询年龄大于
     *
     * @param age 年龄
     * @return 返回值
     */
    Iterable<Student> findAllByAgeAfter(int age);

}
