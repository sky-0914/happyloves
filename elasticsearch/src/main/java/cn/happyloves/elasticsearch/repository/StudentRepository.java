package cn.happyloves.elasticsearch.repository;

import cn.happyloves.elasticsearch.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zc
 * @date 2020/10/23 14:00
 */
public interface StudentRepository extends ElasticsearchRepository<Student, Long> {
}
