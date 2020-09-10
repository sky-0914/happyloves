# redis命令和RedisTemplate操作对应表

+ redisTemplate.opsForValue();//操作字符串
+ redisTemplate.opsForHash();//操作hash
+ redisTemplate.opsForList();//操作list
+ redisTemplate.opsForSet();//操作set
+ redisTemplate.opsForZSet();//操作有序set

| 左对齐 | 右对齐 | 居中对齐 |
| :-----| ----: | :----: |
| 单元格 | 单元格 | 单元格 |
| 单元格 | 单元格 | 单元格 |


| Redis命令行 | RedisTemplate rt | 说明 |
| :-----: | :----: | :----: |
| set k v | rt.opsForValue().set(k,v) | 放入缓存k，v |
| get k | rt.opsForValue().get(k) | 获取k的v |
| del k | rt.opsForValue().delete(k) | 删除k的缓存 |
| del k | rt.opsForValue().delete(k) | 删除k的缓存 |


