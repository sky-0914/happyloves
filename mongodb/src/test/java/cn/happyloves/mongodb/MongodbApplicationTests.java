package cn.happyloves.mongodb;

import cn.happyloves.mongodb.module.Account;
import cn.happyloves.mongodb.module.ColumnUtil;
import cn.happyloves.mongodb.module.LambdaCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;

@SpringBootTest
class MongodbApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {

        System.out.println("字段名：" + ColumnUtil.getName(Account::getId));
        System.out.println("字段名：" + ColumnUtil.getName(Account::getUsername));
        System.out.println("字段名：" + ColumnUtil.getName(Account::getIdNo));
        System.out.println("字段名：" + ColumnUtil.getName(Account::getFamilyAddress));
        System.out.println("字段名：" + ColumnUtil.getName(Account::getAccountInfo));

        Criteria c1 = Criteria.where("id").is("a");
        System.out.println(c1);
        Criteria c2 = new LambdaCriteria<Account>().lambdaWhere(Account::getId).is("a");
        System.out.println(c2);
    }

}
