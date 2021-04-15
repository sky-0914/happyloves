package cn.happyloves.elasticsearch.repository;

import cn.happyloves.elasticsearch.model.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zc
 * @date 2020/10/23 14:00
 */
public interface AccountRepository extends ElasticsearchRepository<Account, Long> {

}
