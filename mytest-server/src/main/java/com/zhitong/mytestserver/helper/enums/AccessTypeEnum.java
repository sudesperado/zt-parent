package com.zhitong.mytestserver.helper.enums;

/**
 * @author : subs
 * @Project: easy_tax
 * @Package com.yzf.easytax.modal.helper.enums
 * @Description:
 * @date Date : 2020年12月07日 16:52
 */
public enum AccessTypeEnum {
    GET(1, "GET");

    private Integer type;
    private String name;

    AccessTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static AccessTypeEnum getByType(Integer type) {
        for (AccessTypeEnum value : AccessTypeEnum.values()) {
            if (type.equals(value.type)) {
                return value;
            }
        }
        return null;
    }
}
