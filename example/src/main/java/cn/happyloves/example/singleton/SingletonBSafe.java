package cn.happyloves.example.singleton;

/**
 * 单例-懒汉模式-双重效验
 * 需要用到的时候再去创建实例
 * volatile关键字来修饰singleton，其最关键的作用是防止指令重排
 * 双重校验的方式，对懒汉式单例模式做了线程安全处理。通过加锁，可以保证同时只有一个线程走到第二个判空代码中去，这样保证了只创建 一个实例
 * <p>
 * 优点：
 * 1、实现简单
 * 2、线程安全
 *
 * @author zc
 * @date 2020/9/5 01:11
 */
public class SingletonBSafe {

    private static volatile SingletonBSafe INSTANCE = null;

    private SingletonBSafe() {
    }

    public static SingletonBSafe getInstance() throws InterruptedException {
        if (null == INSTANCE) {
            synchronized (SingletonBSafe.class) {
                if (null == INSTANCE) {
                    Thread.sleep(500);
                    INSTANCE = new SingletonBSafe();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
//        SingletonBSafe instance1 = SingletonBSafe.getInstance();
//        SingletonBSafe instance2 = SingletonBSafe.getInstance();
//        System.out.println(instance1 == instance2);

        Thread t1 = new Thread(() -> {
            SingletonBSafe b = null;
            try {
                b = SingletonBSafe.getInstance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(b);
        });
        Thread t2 = new Thread(() -> {
            SingletonBSafe b = null;
            try {
                b = SingletonBSafe.getInstance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(b);
        });

        t1.start();
        t2.start();
    }
}
