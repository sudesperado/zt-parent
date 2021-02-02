package com.zhitong.mytestserver.helper.enums;

/**
 * @author : subs
 * @Project: easy_tax
 * @Package com.yzf.easytax.modal.helper.enums
 * @Description:
 * @date Date : 2020年11月25日 14:54
 */
public enum PublishStatusEnum {
    EDIT(1, "编辑"), PUBLISH(2, "发布");

    private Integer type;
    private String name;

    PublishStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public PublishStatusEnum getByPub(Integer type) {
        for (PublishStatusEnum value : PublishStatusEnum.values()) {
            if (type.equals(value.type)) {
                return value;
            }
        }
        return null;
    }
}
