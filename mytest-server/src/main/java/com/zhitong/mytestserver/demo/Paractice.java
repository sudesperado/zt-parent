package com.zhitong.mytestserver.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.demo
 * @Description:
 * @date Date : 2020年11月27日 10:32
 */
public class Paractice {

    public static void main(String[] args) {
        List<MappingResp> esMappingDataList = Lists.newArrayList();
        List<Field> list = Lists.newArrayList();
        list.add( new Field(1,"name","string",0));
        list.add(new Field(2,"age","int",0));
        list.add( new Field(3,"sex","int",0));
        list.add(new Field(4,"info","object",0));
        list.add(new Field(5,"phone","string",4));
        list.add(new Field(6,"address","string",4));
        list.add(new Field(7,"wife","object",0));
        list.add(new Field(8,"name","string",7));
        list.add(new Field(9,"age","int",7));
        list.add(new Field(10,"wifeInfo","object",7));
        list.add(new Field(11,"telphone","string",10));
        list.add(new Field(12,"house","string",10));
        Map<Integer, List<Field>> modalFiledMap = list.stream().collect(Collectors.groupingBy(Field::getParentId));
        getTreeFiled(modalFiledMap,esMappingDataList,0);
        System.out.println(JSON.toJSONString(esMappingDataList));
    }

    private static void getTreeFiled(Map<Integer, List<Field>> modalFiledMap ,List<MappingResp> esMappingDataList,Integer parentId){
        //封装映射列表
        for (Field tmModalField : modalFiledMap.get(parentId)) {
            MappingResp esMappingData = new MappingResp();
            esMappingData.setName(tmModalField.getName());
            esMappingData.setType(tmModalField.getType());
            esMappingDataList.add(esMappingData);
            if (modalFiledMap.containsKey(tmModalField.getId())){
                //有子字段 添加数组或对象标识
//                esMappingData.setIsTree(tmModalField.get());
//                List<Field> modalFieldList = modalFiledMap.get(tmModalField.getId());
                getTreeFiled(modalFiledMap,esMappingData.getMappingRespList(),tmModalField.getId());
            }
        }
    }
}
