package com.hiveview.pms.dto;

import com.hiveview.common.api.BaseEntityDto;

/**
 * Created by leo on 2018/1/15.
 */
public class BasicDataTypeDto extends BaseEntityDto {
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
    private String type;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Boolean getMultipleOnline() {
        return multipleOnline;
    }

    public void setMultipleOnline(Boolean multipleOnline) {
        this.multipleOnline = multipleOnline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}
