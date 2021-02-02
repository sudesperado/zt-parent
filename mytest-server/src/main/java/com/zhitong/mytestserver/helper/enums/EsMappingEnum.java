package com.zhitong.mytestserver.helper.enums;

/**
 * es映射枚举
 */
public enum EsMappingEnum {

    KEYWORD("text", 1, "关键信息-短文字"),
    TEXT("text", 2, "文本段落-长文本"),
    INTEGER("integer", 3, "整数"),
    DOUBLE("double", 4, "小数"),
    DATE("date", 5, "日期"),
    DICT("text", 6, "字典类型"),
    OBJECT("nested", 100, "嵌套模型-对象"),
    ARRAY("nested", 101, "嵌套模型-数组");

    EsMappingEnum(String name, Integer type, String desc) {
        this.name = name;
        this.type = type;
        this.desc = desc;
    }

    private String name;
    private Integer type;
    private String desc;

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static EsMappingEnum getByType(int type) {
        for (EsMappingEnum item : values()) {
            if (item.getType() == type) {
                return item;
            }
        }
        return null;
    }


}
