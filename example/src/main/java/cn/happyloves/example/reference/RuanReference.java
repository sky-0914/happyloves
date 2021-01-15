package cn.happyloves.example.reference;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * 在一个SoftReference有一个对象，该对象占用堆内存，当堆内存容量不够时，回收它。
 * <p>
 * 设置JVM堆内存20M：-Xmx20M
 * <p>
 * 如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
 * 软引用可用来实现内存敏感的高速缓存。
 * <p>
 * 应用场景：缓存，用时缓存起来，当分配给其他对象内存不够使，回收它
 *
 * @author zc
 * @date 2021/1/15 10:44
 */
public class RuanReference {


    public static void main(String[] args) throws InterruptedException {
        //new一个软应用对象，里面存放10M byte数据
        SoftReference<byte[]> sr = new SoftReference<>(new byte[1024 * 1024 * 10]);
        //获取它
        System.out.println(sr.get());
        //GC回收
        System.gc();
        Thread.sleep(1000);
        //再次获取
        System.out.println(sr.get());
        //new一个13M byte对象，
        byte[] b = new byte[1024 * 1024 * 13];
        //此时发现被回收了
        System.out.println(sr.get());
    }

}
