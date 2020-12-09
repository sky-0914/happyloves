package cn.happyloves.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zc
 * @date 2020/12/2 13:07
 */
@Slf4j
public class DemoLock {
    private static int i = 0;

    public static synchronized void test1() throws InterruptedException {
        i++;
        log.info("" + i);
    }

    private static final Lock lock = new ReentrantLock();

    public static void test2() {
        lock.lock();
        i++;
        log.info("" + i);
        lock.unlock();
    }

    public static void main(String[] args) {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                try {
                    DemoLock.test1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "====线程" + j).start();
        }

        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                DemoLock.test2();
            }, "线程" + j).start();
        }

        log.info("结束啦");
    }


}
