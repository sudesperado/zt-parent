package com.zhitong.mytestserver.helper.data;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class EsMappingData {

    /**
     * 字段类型
     */
    private Integer type;

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 子字段属性列表
     */
    private List<EsMappingData> childrenList = Lists.newArrayList();

    public EsMappingData() {

    }

    public EsMappingData(Integer type, String propertyName) {
        this.type = type;
        this.propertyName = propertyName;
    }

}
