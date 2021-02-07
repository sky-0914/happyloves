package cn.happyloves.example.cpuOutOfOrder;

/**
 * 指令乱序，指令重排序
 * <p>
 * 弟2948605次（0,1)
 * 弟2948606次（0,1)
 * 弟2948607次（0,1)
 * 弟2948608次（0,0)
 *
 * @author zc
 * @date 2021/1/15 10:04
 */
public class Demo1 {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; ; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread thread1 = new Thread(() -> {
                a = 1;
                x = b;
            });
            Thread thread2 = new Thread(() -> {
                b = 1;
                y = a;
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            String result = "弟" + i + "次（" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.out.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }
}
