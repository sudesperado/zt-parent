package com.zhitong.mytestserver.contrller;


/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年02月26日 14:27
 */
public class TestLock {
   private static Lock lock = new Lock();

    private static void methodA() throws InterruptedException {
        lock.lock();
        methodB();
        System.out.println("methodA当前线程："+Thread.currentThread().getName());
        lock.unlock();
    }

    private static void methodB() throws InterruptedException {
        lock.lock();
        System.out.println("methodB当前线程："+Thread.currentThread().getName());
        lock.unlock();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                try {
                    methodA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
