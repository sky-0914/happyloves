package cn.happyloves.example.openClosePrinciple;

import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2020/9/21 23:12
 */
@Service(IOpenClosePrinciple.TEST2)
public class Test2Impl implements IOpenClosePrinciple {

    @Override
    public String testOut() {
        String key = "test2";
        System.out.println(key);
        return key;
    }
}
