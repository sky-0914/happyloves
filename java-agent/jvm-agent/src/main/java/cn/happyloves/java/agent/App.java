package cn.happyloves.java.agent;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        List<Object> list = new ArrayList<>();
        while (true) {
            Thread.sleep(500);
            list.add("hello world");
        }
    }
}
