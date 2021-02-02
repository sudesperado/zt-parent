package com.zhitong.mytestserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 时间
     */
    private Date createTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签分类
     */
    private String tagId;

    /**
     * 时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    /**
     * 标签分类
     */
    @TableField(exist = false)
    private String tagName;


}
