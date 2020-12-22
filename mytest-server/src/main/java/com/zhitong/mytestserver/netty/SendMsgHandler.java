package com.zhitong.mytestserver.netty;

import com.alibaba.fastjson.JSON;
import com.zhitong.feignserver.model.netty.BussinessHandlerContext;
import com.zhitong.feignserver.model.netty.NettyHandler;
import com.zhitong.feignserver.model.netty.RpcData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.netty
 * @Description:
 * @date Date : 2020年09月14日 14:17
 */
public class SendMsgHandler extends NettyHandler {
    private Logger logger = LoggerFactory.getLogger(SendMsgHandler.class);

    @Override
    protected void doHandle(BussinessHandlerContext context) throws Exception {

    }

    @Override
    protected RpcData match(BussinessHandlerContext context) {
        if (context.getCmd().equals("SendMsgResponse")) {
            SendMsgResponse sendMsgResponse = JSON.parseObject(context.getMsg(), SendMsgResponse.class);
            logger.info("收到服务端的回执:{}", JSON.toJSONString(sendMsgResponse));
            return sendMsgResponse;
        }
        return null;
    }
}
