package cn.happyloves.example.reference;

/**
 * @author zc
 * @date 2021/1/15 10:39
 */
public class T {

    @Override
    public void finalize() {
        System.out.println("1111111");
    }
}
