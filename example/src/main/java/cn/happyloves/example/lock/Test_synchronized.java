package cn.happyloves.example.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * synchronized
 * 非公平锁：谁抢到锁算谁的，谁就运行
 * 可重入锁
 * 互斥锁
 * 锁升级：无锁->偏向锁->轻量级锁->重量级锁。锁标记是在对象头中的Mark Word中用两位标识00，01，10，11
 * 无锁：对象刚new出来
 * 偏向锁：当只有一个线程调用的时候，将该线程id放入锁中
 * 轻量级锁（自旋锁、CAS）:当有多个线程调用时，升级为自旋锁，一个线程在运行，其他线程在自我旋转等待：默认等待自旋10次，超过10次自动升级重量级锁，可以通过JVM参数指定旋转次数
 * 重量级锁：向OS CPU申请锁，其他线程进入等待队列，等待CPU唤醒
 *
 * @author zc
 * @date 2021/1/15 22:59
 */
@Slf4j
public class Test_synchronized {

    private final Object o = new Object();

    private int count = 0;

    /**
     * 锁在当前类的实例中
     */
    public synchronized void t1() {
        sleep();
        count++;
        log.debug("当前线程: {},count: {}", Thread.currentThread().getName(), count);
    }

    /**
     * 锁在当前类的实例中
     */
    public void t2() {
        synchronized (this) {

        }
    }

    /**
     * 锁在Test_synchronized的Class中
     */
    public void t3() {
        synchronized (Test_synchronized.class) {

        }
    }

    /**
     * 锁在当前类所在的Class中
     */
    public synchronized static void t4() {

    }

    /**
     * 锁在o实例中
     */
    public void t5() {
        synchronized (o) {
            sleep();
            count++;
            log.debug("当前线程: {},count: {}", Thread.currentThread().getName(), count);
        }
    }


    public static void main(String[] args) {
        Test_synchronized ts = new Test_synchronized();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
//                ts.t1();
                ts.t2();
            }, "T" + i).start();
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
