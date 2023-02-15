package cn.happyloves.example.threadpoolexecutor;

import java.util.concurrent.BlockingQueue;

/**
 * @author zc
 * @date 2023/2/7 18:25
 */
public class CustomThreadPool {

    private int maxThreads;
    private int coreThreads;

    private BlockingQueue<Runnable> queue;

    private

    static class MyThread extends Thread{

        @Override
        public void run() {
            super.run();
        }
    }
}
