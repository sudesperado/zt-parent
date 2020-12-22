package com.zhitong.mytestserver.designmode.singleton;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.designmode
 * @Description:
 * @date Date : 2020年12月02日 11:20
 */
public class LazySingleton {

    /*懒汉式单例 在类加载时没有生产单例 只有调用getInstance方法时才会去创建实例*/

    /*保证singleton在所有线程中同步*/
    private static volatile LazySingleton lazySingleton = null;

    /*私有构造方法 确保类不能在外部被实例化*/
    private LazySingleton(){
    }

    private static synchronized LazySingleton getInstance(){
        if (lazySingleton ==null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
