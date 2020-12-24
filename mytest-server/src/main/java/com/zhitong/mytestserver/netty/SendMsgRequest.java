package com.zhitong.mytestserver.netty;

import com.zhitong.mytestserver.model.netty.RpcData;
import lombok.Data;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.securityserver.netty
 * @Description:
 * @date Date : 2020年09月14日 11:28
 */
@Data
public class SendMsgRequest extends RpcData {

    private String msg;

    public SendMsgRequest(){
        this.cmd = "SendMsgRequest";
    }
}
