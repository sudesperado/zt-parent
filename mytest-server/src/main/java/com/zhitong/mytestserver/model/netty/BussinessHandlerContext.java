package com.zhitong.mytestserver.model.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.securityserver.model
 * @Description:
 * @date Date : 2020年09月14日 10:08
 */
public class BussinessHandlerContext {
    /**
     * channel上下文
     */
    private ChannelHandlerContext ctx;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 消息类型
     */
    private String cmd;

    /**
     * 通信实体
     */
    private RpcData rpcData;

    public RpcData getRpcData() {
        return rpcData;
    }

    public void setRpcData(RpcData rpcData) {
        this.rpcData = rpcData;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public BussinessHandlerContext(ChannelHandlerContext ctx, String msg, String cmd){
        this.ctx = ctx;
        this.msg = msg;
        this.cmd = cmd;
    }
}
