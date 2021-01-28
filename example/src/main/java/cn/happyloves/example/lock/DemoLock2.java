package cn.happyloves.example.lock;

/**
 * 多线程锁、锁住同一个对象
 *
 * @author zc
 * @date 2021/1/15 22:59
 */
public class DemoLock2 {

    int count = 0;

    public void test() {
        synchronized (DemoLock2.class) {
            sleep();
            count++;
            System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getState() + "-" + count);
        }
    }

    public synchronized void m() {

    }

    public static void main(String[] args) {
        DemoLock2 d2 = new DemoLock2();
        for (final int[] i = {0}; i[0] < 10; i[0]++) {
            new Thread(() -> {
                d2.test();
            }, "T" + i[0]).start();
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
