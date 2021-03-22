package com.zhitong.mytestserver.contrller;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.BeanFactory;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.contrller
 * @Description:
 * @date Date : 2021年02月26日 14:30
 */
public class Lock{
    private boolean isLocked = false;
    private int count = 0;
    private Thread t = null;

    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (isLocked && t!=currentThread){
            wait();
        }
        isLocked = true;
        count++;
        t = currentThread;
    }

    public synchronized void unlock(){
        Thread currentThread = Thread.currentThread();
        if (t==currentThread){
            count--;
            if (count == 0){
                notify();
                isLocked = false;
            }
        }
    }

    public static void main(String[] args) {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(1,"1");
        map.put(1,"2");
        map.forEach((k,v)-> System.out.println(k+":"+v));
    }
}
