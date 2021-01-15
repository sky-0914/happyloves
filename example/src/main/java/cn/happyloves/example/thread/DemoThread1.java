package cn.happyloves.example.thread;

/**
 * 锁状态
 *
 * @author zc
 * @date 2021/1/15 22:59
 */
public class DemoThread1 {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 1000000000; i++) {

            }
            System.out.println("进入子线程");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.getState());
        t.start();
        System.out.println(t.getState());
        try {
            t.wait();
            System.out.println(t.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        t.notify();
//        System.out.println(t.getState());
//        t.notifyAll();
//        System.out.println(t.getState());
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);

                System.out.println(t.getState());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t.getState());
        }
    }
}
