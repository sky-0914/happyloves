package cn.happyloves.example.hash;

/**
 * @author zc
 * @date 2020/12/15 22:34
 */
public class Test {
//    public static void main(String[] args) {
////        new HashMap<>().put();
////        new HashMap<>(5).size();
////        System.out.println(273498297 >>> 16);
//        System.out.println(16 >>> 3);
//
//        HashMap<Object, Object> map = new HashMap<>();
//        for (int i = 0; i < 20; i++) {
//            map.put(i + "", i + "");
//        }
//    }

    static class A {
        private String a;

        public A(String a) {
            this.a = a;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        @Override
        public int hashCode() {
            return a.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof A) {
                return ((A) o).a.equals(a);
            }
            return false;
        }
    }

    public static void test1(int i) {
        String str = "";
        while (i != 0) {
            str = i % 2 + str;
            i = i / 2;
        }
        System.out.println(str);
    }

    public static void main(String[] args) {
        test1(1873);
        test1(1287);
        test1(8 | 9);

        A a1 = new A("a");
        A a2 = new A("a");

        System.out.println(a1.hashCode() + "," + a1.toString());
        System.out.println(a2.hashCode() + "," + a2.toString());
        System.out.println(a1.equals(a2));
    }
}
