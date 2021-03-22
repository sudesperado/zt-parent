package com.zhitong.mytestserver.contrller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年03月18日 16:29
 */
public class ReadOrWriteLockTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            readLock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1*1000);
                    System.out.println(Thread.currentThread().getName()+"读取数据");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                readLock.unlock();
            }
        });

        executorService.execute(()->{
            writeLock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1*1000);
                    System.out.println(Thread.currentThread().getName()+"写入数据");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                writeLock.unlock();
            }
        });

    }


}
