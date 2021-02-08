package cn.happyloves.example.proxy.cglib;

/**
 * @author zc
 * @date 2021/2/8 16:32
 */
public class LoveService {
    public void dateWith(String name) {
        System.out.println("与" + name + "约会");
    }

    public void hug() {
        System.out.println("拥抱啦~~~");
    }

}
