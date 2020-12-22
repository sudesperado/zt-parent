package com.zhitong.mytestserver.contrller;

import java.util.Random;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.contrller
 * @Description:
 * @date Date : 2020年12月14日 14:25
 */
public class AcountTest {
    public static void main(String[] args) {
        Account account = new Account(100);
        Account target = new Account(100);


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 10 ;
                while (num > 0){
                    num--;
                    Random random = new Random();
                    int m = random.nextInt(10);
                    synchronized (Account.class){
                        account.charge(target,m);
                        target.charge(account,m);
                        System.out.println(Thread.currentThread().getName()+":"+account.getMoney()+"/"+target.getMoney()+"/"+m);
                    }
            }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 10 ;
                while (num > 0){
                    num--;
                    Random random = new Random();
                    int m = random.nextInt(10);
                    synchronized (Account.class){
                        account.charge(target,m);
                        target.charge(account,m);
                        System.out.println(Thread.currentThread().getName()+":"+account.getMoney()+"/"+target.getMoney()+"/"+m);
                    }
                }
            }
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getMoney()+target.getMoney());
    }
}
