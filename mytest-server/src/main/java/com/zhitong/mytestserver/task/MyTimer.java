package com.zhitong.mytestserver.task;

import com.zhitong.mytestserver.model.User;
import com.zhitong.mytestserver.service.MyUserService;
import com.zhitong.mytestserver.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.task
 * @Description:
 * @date Date : 2020年12月28日 10:13
 */
public class MyTimer<T> extends TimerTask {

    private T data;

    public MyTimer(){
    }

    public MyTimer(T data){
        this.data = data;
    }

    private Logger logger = LoggerFactory.getLogger(MyTimer.class);

    @Override
    public void run() {
        logger.info("开启线程、定时执行了");

        User u =(User) data;
        logger.info("名称是：{}",u);
        MyUserService myUserService = (MyUserService) ApplicationUtil.getBean(MyUserService.class);
        String user = myUserService.getUser();
        logger.info("注入成功：{}",user);
    }
}
