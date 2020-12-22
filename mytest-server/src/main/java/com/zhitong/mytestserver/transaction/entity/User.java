package com.zhitong.mytestserver.transaction.entity;

import lombok.Data;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.transaction.entity
 * @Description:
 * @date Date : 2020年11月27日 14:17
 */
@Data
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String address;
}
