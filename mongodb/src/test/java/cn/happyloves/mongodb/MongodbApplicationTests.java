package cn.happyloves.mongodb;

import cn.happyloves.mongodb.lambda.Lmcw;
import cn.happyloves.mongodb.module.OrderInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;

@SpringBootTest
class MongodbApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {

        System.out.println(Lmcw.create(OrderInfo::getId));

        Criteria criteria = new Criteria();

//        criteria.and(Lmcw.create(OrderInfo::getId))
    }

}
