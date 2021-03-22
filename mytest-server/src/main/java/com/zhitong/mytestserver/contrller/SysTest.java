package com.zhitong.mytestserver.contrller;

import com.alibaba.druid.support.json.JSONUtils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年03月18日 9:54
 */
public class SysTest {

    private static CountDownLatch runerLatch = new CountDownLatch(2);

    public static void main(String[] args) {

       //两个乘客线程
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("乘客"+Thread.currentThread().getName()+"从酒店出发");
                        Thread.sleep(new Random().nextInt(10));
                        System.out.println("乘客"+Thread.currentThread().getName()+"已上车");
                        runerLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //司机线程
        try {
            System.out.println("等待乘客上车");
            runerLatch.await();
            System.out.println("发车！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
