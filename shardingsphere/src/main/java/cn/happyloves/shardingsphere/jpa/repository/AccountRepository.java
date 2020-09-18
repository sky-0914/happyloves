package cn.happyloves.shardingsphere.jpa.repository;

import cn.happyloves.shardingsphere.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zc
 * @date 2020/9/17 17:25
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
