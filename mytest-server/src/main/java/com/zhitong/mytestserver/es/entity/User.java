package com.zhitong.mytestserver.es.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.es.entity
 * @Description:
 * @date Date : 2020年11月23日 13:48
 */
@Data
public class User {
    private String name;
    private Integer age;
    private String sex;
    private String address;
    private Date birthday;

    /*
     * 字段名称：userList
     * 字段类型 ：嵌套模型（101）
     * 嵌套类型分类：数组类型 （1）
     *
     * */
    private List<User> userList;
}
