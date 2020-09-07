package cn.happyloves.example.singleton;

/**
 * 单例-枚举
 *
 * @author zc
 * @date 2020/9/5 01:17
 */
public class SingletonC {

    private SingletonC() {
    }

    enum SingletonCEnum {
        /**
         * 创建一个枚举对象，该对象天生为单例
         */
        INSTANCE;
        private SingletonC singletonC;

        SingletonCEnum() {
            singletonC = new SingletonC();
        }
    }

    public static SingletonC getInstance() {
        return SingletonCEnum.INSTANCE.singletonC;
    }

    public static void main(String[] args) {
        SingletonC instance1 = SingletonC.getInstance();
        SingletonC instance2 = SingletonC.getInstance();
        System.out.println(instance1 == instance2);
    }

}
