package cn.happyloves.example.threadpoolexecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZC
 * @date 2021/2/8 21:04
 */
public class ThreadTest {
    public static void main(String[] args) {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue(2));
//        for (int i = 0; i < 8; i++) {
//            int finalI = i;
//            threadPoolExecutor.execute(()->{
//                System.out.println(Thread.currentThread().getName()+"===>>>"+ finalI);
//            });
//            threadPoolExecutor.
//        }
//        threadPoolExecutor.shutdown();
//        Executors.newSingleThreadExecutor();
//        new Thread()

//        Thread thread = new Thread(() -> {
//            System.out.println(111111);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(22222);
//        });
//
//        System.out.println(thread.getState());
//        thread.start();
//        System.out.println(thread.getState());
//        System.out.println(thread.getState());
//        System.out.println(thread.getState());

        new Thread().start();
    }
}
