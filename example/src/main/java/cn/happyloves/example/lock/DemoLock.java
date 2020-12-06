package cn.happyloves.example.lock;

/**
 * @author zc
 * @date 2020/12/2 13:07
 */
public class DemoLock {
    private static int i = 0;

    public static synchronized void test1() {
        i++;
        System.out.println(i);
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            DemoLock.test1();
        });
        thread.start();
    }
}
