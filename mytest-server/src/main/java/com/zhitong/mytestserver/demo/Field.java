package com.zhitong.mytestserver.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.demo
 * @Description:
 * @date Date : 2020年11月27日 10:29
 */
@Data
@AllArgsConstructor
public class Field {

    private Integer id;
    private String name;
    private String type;
    private Integer parentId;
}
