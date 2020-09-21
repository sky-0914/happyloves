package cn.happyloves.example.openClosePrinciple;

import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2020/9/21 23:12
 */
@Service(IOpenClosePrinciple.TEST1)
public class Test1Impl implements IOpenClosePrinciple {

    @Override
    public String testOut() {
        String key = "test1";
        System.out.println(key);

        return key;
    }
}
