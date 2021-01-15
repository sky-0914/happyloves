package cn.happyloves.example.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 * <p>
 * 虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
 * 虚引用主要用来跟踪对象被垃圾回收器回收的活动，且虚引用必须和引用队列（ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
 * <p>
 * 程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。
 * <p>
 * 应用场景：主要用来管理堆外内存。NIO，Netty底层实现
 *
 * @author zc
 * @date 2021/1/15 13:20
 */
public class XuReference {
    public static void main(String[] args) {
        T t = new T();
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference r = new PhantomReference(t, queue);
        //获取它
        System.out.println(r.get());
        //GC回收
        System.gc();
        //再次获取，发现被回收了
        System.out.println(r.get());
    }
}
