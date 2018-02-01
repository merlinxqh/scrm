package com.hiveview.pms.entity.basic;

import com.hiveview.base.common.BaseEntity;
import lombok.Data;

/**
 * 
 * @author hiveview
 * @date 2018-01-15 17:01:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Data
public class BasicData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关联类型code.
     */
    private String typeCode;

    /**
     * 长编码.
     */
    private String typeLongCode;

    /**
     * 标签.
     */
    private String label;

    /**
     * 值.
     */
    private String value;

    /**
     * 描述.
     */
    private String description;

    /**
     * 排序.
     */
    private Integer orders;

    /**
     * 状态 1:启用,2:禁用.
     */
    private Integer status;

    /**
     * 删除标识 1:正常, 2:已删除
     */
    private Integer delFlag;

}