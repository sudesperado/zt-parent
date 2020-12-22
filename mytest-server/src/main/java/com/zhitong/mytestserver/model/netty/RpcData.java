package com.zhitong.mytestserver.model.netty;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.securityserver.model
 * @Description:
 * @date Date : 2020年09月14日 10:08
 */
public abstract class RpcData {
    public static final String Netty_Delimiter = "$$_$$";

    protected String cmd;

    protected String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCmd() {
        return cmd;
    }
}
