package cn.happyloves.example.thread;

/**
 * 线程状态
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

        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(2);
                System.out.println(t.getState());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
