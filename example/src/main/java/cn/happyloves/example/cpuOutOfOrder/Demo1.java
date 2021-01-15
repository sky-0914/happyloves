package cn.happyloves.example.cpuOutOfOrder;

/**
 * 指令乱序，指令重排序
 *
 * @author zc
 * @date 2021/1/15 10:04
 */
public class Demo1 {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; ; i++) {
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread other = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            other.start();
            one.join();
            other.join();
            if (x == 0 && y == 0) {
                System.out.println(i + "(" + x + "," + y + ")");
                break;
            }
        }


    }
}
