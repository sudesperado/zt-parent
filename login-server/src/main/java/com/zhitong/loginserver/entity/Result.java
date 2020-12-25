package com.zhitong.loginserver.entity;

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

    private Result(){
    }

    public static Result newInstance(){
        return new Result();
    }



    public Result filed(String message){
        this.code = 500;
        this.message = message;
        return this;
    }

    public Result success(String message,T data){
        this.code = 200;
        this.message = message;
        this.data = data;
        return this;
    }
}
