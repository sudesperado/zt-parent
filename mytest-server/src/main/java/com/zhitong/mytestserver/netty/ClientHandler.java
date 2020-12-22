package com.zhitong.mytestserver.netty;

import com.alibaba.fastjson.JSON;
import com.zhitong.feignserver.model.netty.BussinessHandlerContext;
import com.zhitong.feignserver.model.netty.NettyHandler;
import com.zhitong.feignserver.model.netty.RpcData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * netty客户端handler
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private NettyHandler bussinessHandler;


    public ClientHandler(){
        bussinessHandler = new SendMsgHandler();
    }

    /**
     * 读取服务器端返回的数据(远程调用的结果)
     * @param ctx
     * @param obj
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        try {
            String msg = (String)obj;
            String cmd = JSON.parseObject(msg).getString("cmd");
            BussinessHandlerContext bussinessHandlerContext = new BussinessHandlerContext(ctx, msg, cmd);
            bussinessHandler.handle(bussinessHandlerContext);
        }
        catch (Exception ex){
            logger.error("Netty channel read 异常", ex);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        logger.info("Netty channel active:{}", ctx.channel().remoteAddress());
        ctx.channel();
        //　连接建立后发送登录请求
        login(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 发生异常，关闭链路
        ctx.close();
//        Env.NettyLogined.set(false);
    }

    /**
     * 发送登录请求
     */
    private void login(ChannelHandlerContext ctx) {
        try {
            SendMsgRequest sendMsgRequest = new SendMsgRequest();
            sendMsgRequest.setMsg("客户端发个消息给服务端!!!");
            logger.info("发送消息内容:{}", JSON.toJSONString(sendMsgRequest));
            ctx.channel().writeAndFlush(JSON.toJSONString(sendMsgRequest) + RpcData.Netty_Delimiter);
        }
        catch (Exception ex) {
            logger.error("发送登录请求异常！", ex);
            ctx.close();
        }
    }

}