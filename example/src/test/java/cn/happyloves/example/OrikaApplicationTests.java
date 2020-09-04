package cn.happyloves.example;

import cn.happyloves.example.dto.Account;
import cn.happyloves.example.dto.Friend;
import cn.happyloves.example.dto.UserA;
import cn.happyloves.example.dto.UserB;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OrikaApplicationTests {

    @Test
    void contextLoads() {

        Account account = new Account();
        account.setId(1);
        account.setUsername("zhangsan");
        account.setPassword("zhangsan");

        Friend friend1 = new Friend();
        friend1.setA("a");
        friend1.setB("a");
        Friend friend2 = new Friend();
        friend2.setA("b");
        friend2.setB("b");
        List<Friend> friendListA = new ArrayList<>();
        friendListA.add(friend1);
        friendListA.add(friend2);

        UserA userA = new UserA();
        userA.setId(1);
        userA.setName("张三");
        userA.setAge(18);
        userA.setAccount(account);
        userA.setFriends(friendListA);
        userA.setA("A");

        UserB userB = new UserB();
        List<Friend> friendListB = new ArrayList<>();
        Friend friend3 = new Friend();
        friend3.setA("c");
        friend3.setB("c");
        friendListB.add(friend3);
        userB.setFriends(friendListB);

        System.out.println(userA);
        System.out.println(userB);

        //转换
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        MapperFacade mapper = mapperFactory.getMapperFacade();
//        UserB userB = mapper.map(userA, UserB.class);
        mapper.map(userA, userB);
        System.out.println(userA);
        System.out.println(userB);
    }

}
