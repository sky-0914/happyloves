package cn.happyloves.example.singleton;

/**
 * 单例-懒汉模式
 * 需要用到的时候再去创建实例
 * <p>
 * 优点：
 * 1、实现简单
 * 缺点：
 * 1、线程不安全的。在并发获取实例的时候，可能会存在构建了多个实例的情况
 *
 * @author zc
 * @date 2020/9/5 01:11
 */
public class SingletonB {
    private static SingletonB INSTANCE = null;

    private SingletonB() {
    }

    public static SingletonB getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SingletonB();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonB instance1 = SingletonB.getInstance();
        SingletonB instance2 = SingletonB.getInstance();
        System.out.println(instance1 == instance2);
    }
}
