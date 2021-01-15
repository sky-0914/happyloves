package cn.happyloves.example.singleton;

/**
 * 单例-饿汉模式
 * 类被加载的时候就把对象实例化了
 * <p>
 * 优点：
 * 1、实现简单
 * 2、线程安全的
 * 缺点：
 * * 1、没有懒加载，在不需要的此实例的时候就已经把实例创建出来了
 *
 * @author zc
 * @date 2020/9/5 00:54
 */
public class SingletonA {
    private static final SingletonA INSTANCE = new SingletonA();

    private SingletonA() {
    }

    public static SingletonA getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonA instance1 = SingletonA.getInstance();
        SingletonA instance2 = SingletonA.getInstance();
        System.out.println(instance1 == instance2);

        Thread t1 = new Thread(() -> {
            SingletonA a = SingletonA.getInstance();
            System.out.println(a);
        });
        Thread t2 = new Thread(() -> {
            SingletonA a = SingletonA.getInstance();
            System.out.println(a);
        });

        t1.start();
        t2.start();

    }
}
