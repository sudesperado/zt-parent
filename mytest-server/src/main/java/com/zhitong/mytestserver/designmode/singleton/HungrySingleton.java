package com.zhitong.mytestserver.designmode.singleton;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.designmode.singleton
 * @Description:
 * @date Date : 2020年12月02日 11:33
 */
public class HungrySingleton {
    /*饿汉式 类一旦被加载 实例对象就被创建出来*/
    private static final HungrySingleton instance=new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton getInstance()
    {
        return instance;
    }
}
