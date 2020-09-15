package cn.happyloves.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zc
 * @date 2020/9/14 19:49
 */
@CacheConfig(cacheNames = {"cache"})
@Slf4j
@Service
public class CacheService {

    private List<StudentDTO> list;

    public CacheService() {
        log.info("实例化数组");
        this.list = new ArrayList<>();
        list.add(StudentDTO.builder()
                .id(1L)
                .age(18)
                .name("张三")
                .build());
        list.add(StudentDTO.builder()
                .id(2L)
                .age(20)
                .name("李四")
                .build());
    }

    @Cacheable
    public List<StudentDTO> getAll() {
        log.info("查询数据");
        return list;
    }

    @Cacheable
    public List<StudentDTO> getOne(Long id) {
        log.info("查询单条数据");
        return list.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
    }

    @CacheEvict(keyGenerator = "saveGenerator")
    public List<StudentDTO> save(StudentDTO dto) {
        log.info("保存数据");
        List<StudentDTO> newList = new ArrayList<>();
        list.forEach(s -> {
            if (s.getId().equals(dto.getId())) {
                newList.add(dto);
            } else {
                newList.add(s);
            }
        });
        this.list = newList;
        return list;
    }
}
