package cn.happyloves.example.reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 * 一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
 * 软引用和弱引用都可以和一个引用队列（ReferenceQueue）联合使用，如果所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用或软引用加入到与之关联的引用队列中。
 * ThreadLocal类的静态内部类ThreadLocalMap的静态内部类Entry中的k,就是弱引用
 * <p>
 * 应用场景：ThreadLocal，@Transactional就是用ThreadLocal实现的
 *
 * @author zc
 * @date 2021/1/15 13:12
 */
public class RuoReference {

    public static void main(String[] args) throws InterruptedException {
        //new一个软应用对象，里面存放10M byte数据
        WeakReference<byte[]> sr = new WeakReference<>(new byte[1024 * 1024 * 10]);
        //获取它
        System.out.println(sr.get());
        //GC回收
        System.gc();
        //再次获取，发现被回收了
        System.out.println(sr.get());
    }
}
