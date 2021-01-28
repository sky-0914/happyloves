package cn.happyloves.example.thread;

import java.util.Arrays;
import java.util.List;

/**
 * 两个线程打印A1B2...Z26
 * 一个线程打印A-Z
 * 一个线程打印1-26
 *
 * @author zc
 * @date 2021/1/15 22:59
 */
public class DemoThread2 {
    static final Object o = new Object();
    static List<String> list1 = Arrays.asList("A", "B", "C", "D");
    static List<String> list2 = Arrays.asList("1", "2", "3", "4");

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            synchronized (o) {
                for (String s : list1) {
                    System.out.println(s);
                    try {
                        o.wait();
                        o.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("线程一结束");
        }, "线程一");

        t2 = new Thread(() -> {
            synchronized (o) {
                for (String s : list2) {
                    System.out.println(s);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("线程二结束");
        }, "线程二");

        t1.start();
        t2.start();
    }
}
