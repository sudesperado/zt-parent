package com.zhitong.mytestserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.model
 * @Description:
 * @date Date : 2020年08月14日 14:00
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;
}
