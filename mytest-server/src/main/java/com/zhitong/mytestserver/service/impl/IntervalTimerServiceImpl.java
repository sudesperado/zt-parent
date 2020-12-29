package com.zhitong.mytestserver.service.impl;

import com.zhitong.mytestserver.model.User;
import com.zhitong.mytestserver.service.IntervalTimerService;
import com.zhitong.mytestserver.task.MyTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Timer;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.service.impl
 * @Description:
 * @date Date : 2020年12月28日 10:11
 */
@Service
public class IntervalTimerServiceImpl implements IntervalTimerService {
    private Logger logger = LoggerFactory.getLogger(IntervalTimerService.class);

    @Override
    public String getIntervalTimer(User user) {
        logger.info("开始执行定时任务");

        MyTimer myTimer = new MyTimer(user);

        new Timer().schedule(myTimer,10000);

        logger.info("结束");
        return "1";
    }
}
