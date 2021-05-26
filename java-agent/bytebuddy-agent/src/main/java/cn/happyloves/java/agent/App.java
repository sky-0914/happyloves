package cn.happyloves.java.agent;

/**
 * Hello world!
 */
public class App {
    private void fun1() throws Exception {
        System.out.println("this is fun 1.");
        Thread.sleep(500);
    }

    private void fun2() throws Exception {
        System.out.println("this is fun 2.");
        Thread.sleep(500);
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.fun1();
        app.fun2();
        System.out.println("Hello World!");

    }
}
