package cn.happyloves.example.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zc
 * @date 2021/1/15 22:59
 */
public class DemoThread3 {

    static List<Object> list = new ArrayList<>();

    public void add(Object obj) {
        list.add(obj);
    }

    public int size() {
        return list.size();
    }


    static final Object o = new Object();

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 1; i <= 10; i++) {
                    System.out.println(i);
                }
            }
            System.out.println("线程一结束");
        }, "线程一");

        t2 = new Thread(() -> {
//            synchronized (o) {
            while (true) {
                if (list.size() == 5) {
                    break;
                }
//                }
            }
            System.out.println("线程二结束");
        }, "线程二");

        t2.start();
        t1.start();

    }
}
