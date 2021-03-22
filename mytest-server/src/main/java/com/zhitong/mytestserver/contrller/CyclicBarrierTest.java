package com.zhitong.mytestserver.contrller;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年03月18日 15:13
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int totalNum = 200;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(25,()->{
            System.out.println("大巴要出发了！");
        });

        for (int i = 0; i < totalNum; i++) {
            new Thread(new Travel("people-"+i,cyclicBarrier)).start();
        }
    }

    static class Travel implements Runnable{

        private String name;
        private CyclicBarrier cyclicBarrier;

        public Travel(String name,CyclicBarrier cyclicBarrier){
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(name+"已上车,等待其他乘客");
                Thread.sleep(new Random().nextInt(10));
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
