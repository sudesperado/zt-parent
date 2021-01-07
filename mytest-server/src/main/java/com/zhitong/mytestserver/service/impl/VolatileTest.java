package com.zhitong.mytestserver.service.impl;


/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.service.impl
 * @Description:
 * @date Date : 2020年12月30日 14:49
 */
public class VolatileTest {
    private static volatile Integer num = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num<100){
                    System.out.println(Thread.currentThread().getName()+"----"+num++);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (num<100){
                    System.out.println(Thread.currentThread().getName()+"----"+num++);
                }
            }
        }).start();
    }
}
