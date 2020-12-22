package com.zhitong.mytestserver.demo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.demo
 * @Description:
 * @date Date : 2020年11月27日 10:31
 */
@Data
public class MappingResp {
    private String name;
    private String type;
    private Integer isTree;
    private List<MappingResp> mappingRespList = Lists.newArrayList();
}
