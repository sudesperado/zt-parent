package com.zhitong.mytestserver.helper;

import com.alibaba.fastjson.JSONObject;

import com.zhitong.mytestserver.helper.data.EsMappingData;
import com.zhitong.mytestserver.helper.enums.EsMappingEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EsMappingHelper {

    /**
     * 构建ES初始化——mapping
     *
     * @param inputDatas
     * @return
     */
    public JSONObject createInitMapping(List<EsMappingData> inputDatas) {
        JSONObject mappings = new JSONObject();
        JSONObject properties = new JSONObject();

        //默认ID属性
        JSONObject idProperty = new JSONObject();
        idProperty.put("type", "keyword");
        properties.put("_dataId", idProperty);

        //创建人
        JSONObject creator = new JSONObject();
        creator.put("type", "long");
        properties.put("_creator", creator);

        //更新人
        JSONObject updator = new JSONObject();
        updator.put("type", "long");
        properties.put("_updator", updator);

        //创建时间
        JSONObject createTime = new JSONObject();
        createTime.put("type", "date");
        properties.put("_createTime", createTime);

        //修改时间
        JSONObject updateTime = new JSONObject();
        updateTime.put("type", "date");
        properties.put("_updateTime", updateTime);

        //自定义的属性
        createProperties(inputDatas,properties);

        mappings.put("properties", properties);
        return mappings;
    }

    /**
     * 创建ES修改mapping
     *
     * @param inputDatas
     * @return
     */
    public JSONObject createEsUpdMapping(List<EsMappingData> inputDatas) {
        JSONObject mappings = new JSONObject();
        JSONObject properties = new JSONObject();
           //自定义的属性
        createProperties(inputDatas,properties);

        mappings.put("properties", properties);
        return mappings;
    }

    /**
     * 自定义映射属性
     */
    private void createProperties(List<EsMappingData> inputDatas , JSONObject properties){
        //自定义的属性
        for (EsMappingData in : inputDatas) {
            JSONObject property = new JSONObject();
            if (in.getType().equals(EsMappingEnum.TEXT.getType()) || in.getType().equals(EsMappingEnum.KEYWORD.getType())) {
                property.put("type", "text");
                JSONObject fields = new JSONObject();
                JSONObject keyword = new JSONObject();
                keyword.put("type", "keyword");
                keyword.put("ignore_above", 256);
                fields.put("keyword", keyword);
                property.put("fields", fields);
            } else if (in.getType().equals(EsMappingEnum.INTEGER.getType())) {
                property.put("type", EsMappingEnum.INTEGER.getName());
            } else if (in.getType().equals(EsMappingEnum.DOUBLE.getType())) {
                property.put("type", EsMappingEnum.DOUBLE.getName());
            } else if (in.getType().equals(EsMappingEnum.DATE.getType())) {
                property.put("type", EsMappingEnum.DATE.getName());
            } else if (in.getType().equals(EsMappingEnum.DICT.getType())) {
                property.put("type", EsMappingEnum.DICT.getName());
            } else if(in.getType().equals(EsMappingEnum.OBJECT.getType())) {
                property.put("type", EsMappingEnum.OBJECT.getName());
                if (CollectionUtils.isNotEmpty(in.getChildrenList())){
                    JSONObject innerProperty = new JSONObject();
                    createProperties(in.getChildrenList(),innerProperty);
                    property.put("properties",innerProperty);
                }
            } else if (in.getType().equals(EsMappingEnum.ARRAY.getType())) {
                property.put("type", EsMappingEnum.ARRAY.getName());
                if (CollectionUtils.isNotEmpty(in.getChildrenList())){
                    JSONObject innerProperty = new JSONObject();
                    createProperties(in.getChildrenList(),innerProperty);
                    property.put("properties",innerProperty);
                }
            }
            properties.put(in.getPropertyName(), property);
        }
    }

}
