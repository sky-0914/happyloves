package cn.happyloves.h2db.jpa;

import cn.happyloves.h2db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zc
 * @date 2020/8/19 17:03
 */
//JpaRepository<实体类, 主键ID类型>
public interface AccountJPA extends JpaRepository<Account, Integer> {
}
