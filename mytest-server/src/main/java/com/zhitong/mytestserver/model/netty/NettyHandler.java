package com.zhitong.mytestserver.model.netty;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.securityserver.model
 * @Description:
 * @date Date : 2020年09月14日 10:07
 */
public abstract class NettyHandler {
    private NettyHandler nextHandler = null;

    public void handle(BussinessHandlerContext context) throws Exception {
        RpcData rpcData = match(context);
        if (rpcData != null){
            context.setRpcData(rpcData);
            doHandle(context);
        }
        if (nextHandler != null){
            this.nextHandler.handle(context);
        }
    }

    public NettyHandler join(NettyHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    protected abstract void doHandle(BussinessHandlerContext context) throws Exception;

    protected abstract RpcData match(BussinessHandlerContext context);
}
