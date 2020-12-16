package cn.happyloves.example.hash;

import java.util.HashMap;

/**
 * @author zc
 * @date 2020/12/10 21:19
 * 默认所有对象的父类都是Object，Object的equals的方法是判断==也就是比较内存地址是否一样；
 * hashcode表示的是JVM虚拟机为这个Object对象分配的一个int类型的数值，JVM会使用对象的hashcode值来提高对HashMap、Hashtable哈希表存取对象的使用效率。
 */
//@Data //Lombok注解实现了equals和hashcode的重写。
public class TestHash {
    private int id;

    public TestHash() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TestHash(int id) {
        this.id = id;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof TestHash) {
//            if (((TestHash) obj).id == this.id) {
//                return true;
//            }
//        }
//        return false;
//    }

//    @Override
//    public int hashCode() {
//        return this.id;
//    }


    public static void main(String[] args) {
        TestHash t1 = new TestHash();
        TestHash t2 = new TestHash();
        System.out.println(t1.hashCode());
        System.out.println(t2.hashCode());
        System.out.println(t1.equals(t2));

        Integer i1=1;
        Integer i2=1;
        System.out.println(i1.hashCode());
        System.out.println(i1.hashCode());
        System.out.println(i1.equals(i2));

        String s1 = "1";
        String s2 = "1";
        System.out.println(s1.hashCode());
        System.out.println(s1.hashCode());
        System.out.println(s1.equals(s2));

    }
}
