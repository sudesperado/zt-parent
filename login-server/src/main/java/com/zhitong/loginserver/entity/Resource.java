package com.zhitong.loginserver.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author su
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resId;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 0-不可用 1可用
     */
    private String state;

    /**
     * 资源类型 1-menu
     */
    private String type;

    /**
     * URL
     */
    private String url;

    /**
     * 排序
     */
    private String viewSeq;


}
