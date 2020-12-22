package com.zhitong.mytestserver.contrller;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.contrller
 * @Description:
 * @date Date : 2020年12月14日 14:21
 */
@Data
@AllArgsConstructor
public class Account {
    private Integer money;

    public void charge(Account target , Integer yue){
        this.money = this.money-yue;
        target.money = target.money+yue;
    }

    public static void main(String[] args) {
        Integer i = 786646659;
        Long l = Long.valueOf(i);
        String format = String.format("这个数字是%d", l);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder hello_ = stringBuilder.append("hello ").append(l);
        System.out.println(format);
        System.out.println(hello_.toString());
    }
}
