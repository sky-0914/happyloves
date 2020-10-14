package cn.happyloves.shardingsphere.jpa.repository;

import cn.happyloves.shardingsphere.jpa.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zc
 * @date 2020/9/17 17:25
 */
public interface TestRepository extends JpaRepository<Test, Integer> {

    List<Test> getAllByAgeGreaterThanEqual(Integer age);
}
