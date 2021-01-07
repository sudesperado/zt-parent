package com.zhitong.mytestserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.inject.internal.cglib.core.$ObjectSwitchCallback;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author su
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PunchCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 周数
     */
    private Integer week;

    /**
     * 周期
     */
    private String period;

    /**
     * 事情描述
     */
    private String doThing;

    /**
     * 是否完成 （0：未完成）
     */
    private Integer isOver;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户
     */
    @TableField("user_id")
    private Long userId;

    @TableField(exist = false)
    private LocalDateTime createTime;


}
