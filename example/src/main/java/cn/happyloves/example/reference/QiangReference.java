package cn.happyloves.example.reference;

/**
 * @author zc
 * @date 2021/1/15 10:39
 * 强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。当内存空间不足，Java虚拟机宁愿抛出OOM，也不会靠随意回收具有强引用的对象来解决内存不足的问题。
 */
public class QiangReference {

    public static void main(String[] args) {
        T t = new T();
        System.out.println(t);
        t = null;
        System.out.println(t);
        System.gc();
        System.out.println(t);
    }
}
