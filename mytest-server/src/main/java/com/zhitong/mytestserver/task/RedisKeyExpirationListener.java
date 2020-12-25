package com.zhitong.mytestserver.task;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

/**
 * @author : subs
 * @Project: zt-parent
 * @Package com.zhitong.mytestserver.task
 * @Description:
 * @date Date : 2020年12月25日 17:22
 */
@Service
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //获取过期的key
        String expireKey = message.toString();
        System.out.println(expireKey+"终于失效了");
    }
}
