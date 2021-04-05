package cn.happyloves.mongodb;

import cn.happyloves.mongodb.module.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.function.Function;

@SpringBootTest
class MongodbApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {

        Function<Account, String> times2 = fun -> {
            Account a = new Account();
            return a.getName();
        };

//        System.out.println("字段名：" + ColumnUtil.getName(Account::getId));
//        System.out.println("字段名：" + ColumnUtil.getName(Account::getUsername));
//        System.out.println("字段名：" + ColumnUtil.getName(Account::getIdNo));
//        System.out.println("字段名：" + ColumnUtil.getName(Account::getFamilyAddress));
        System.out.println("字段名：" + ColumnUtil.getName(Account::getAccountInfo));
//        System.out.println("字段名：" + ColumnUtil.getName(Account::getAccountInfo, Account::getAccountInfo));
////        System.out.println("字段名：" + new LambdaCriteria<Account>().lambdaWhereStr(Account::getAccountInfo, TestA::getAge));
//
//        Criteria c1 = Criteria.where("id").is("a");
//        System.out.println(c1);
//        Criteria c2 = new LambdaCriteria<Account>().lambdaWhere(Account::getId).is("a");
//        System.out.println(c2);


        final LambdaCriteria<Account> accountLambdaCriteria = new LambdaCriteria<>(Account::getTestA);
        final LambdaCriteria<TestA> testALambdaCriteria = new LambdaCriteria<>(TestA::getTestB);
        final LambdaCriteria<TestC> testCLambdaCriteria = new LambdaCriteria<>(TestC::getTestC);
        accountLambdaCriteria.addSon(testALambdaCriteria).addSon(testCLambdaCriteria);
        System.out.println(accountLambdaCriteria.getColumn());

//        final LambdaCriteria<?> lambdaCriteria = new LambdaCriteria<>(Account::getAccount).addSon(new LambdaCriteria<>(Account::getUsername));
//        System.out.println(lambdaCriteria.getColumn());

    }

}
