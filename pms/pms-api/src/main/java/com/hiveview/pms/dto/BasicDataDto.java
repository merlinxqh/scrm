package com.hiveview.pms.dto;

import com.hiveview.common.api.BaseEntityDto;

/**
 * Created by leo on 2018/1/15.
 */
public class BasicDataDto extends BaseEntityDto {
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
     * 删除标识 1: 正常, 2:已删除
     */
    private Integer delFlag;

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeLongCode() {
        return typeLongCode;
    }

    public void setTypeLongCode(String typeLongCode) {
        this.typeLongCode = typeLongCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
