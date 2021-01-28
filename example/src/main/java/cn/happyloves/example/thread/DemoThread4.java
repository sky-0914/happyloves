package cn.happyloves.example.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zc
 * @date 2021/1/15 22:59
 */
public class DemoThread4 {
    static class FizzBuzz {
        private int n;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public synchronized void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0) {
                    printFizz.run();
                    this.notifyAll();
                } else {
                    System.out.println("333");
                    this.wait();
                }

            }
        }

        // printBuzz.run() outputs "buzz".
        public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 5 == 0) {
                    printBuzz.run();
                    this.notifyAll();
                } else {
                    System.out.println("555");
                    this.wait();
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 5 == 0 && i % 3 == 0) {
                    printFizzBuzz.run();
                    this.notifyAll();
                } else {
                    System.out.println("333555");
                    this.wait();
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public synchronized void number() throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 5 > 0 && i % 3 > 0) {
                    System.out.println(i);
                    this.notifyAll();
                } else {
                    this.wait();
                }
            }
        }
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(10);
        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> {
                    System.out.println("fizz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> {
                    System.out.println("fizz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> {
                    System.out.println("fizz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
