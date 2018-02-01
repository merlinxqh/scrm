package com.hiveview.pms.entity.basic;

import com.hiveview.base.common.BaseEntity;
import com.hiveview.pms.enums.BasicDataTypeEnum;
import lombok.Data;

/**
 * 
 * @author hiveview
 * @date 2018-01-15 17:01:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Data
public class BasicDataType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编码.
     */
    private String code;

    /**
     * 父级编码.
     */
    private String parentCode;

    /**
     * 是否允许多个同时上线(启用).
     */
    private Boolean multipleOnline;

    /**
     * 数据类型分类.
     */
    private BasicDataTypeEnum type;

    /**
     * 长编码.
     */
    private String longCode;

    /**
     * 名称.
     */
    private String name;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 级别.
     */
    private Integer level;

    /**
     * 排序.
     */
    private Integer orders;
}