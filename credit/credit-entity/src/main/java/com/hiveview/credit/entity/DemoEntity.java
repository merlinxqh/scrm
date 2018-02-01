package com.hiveview.credit.entity;

import com.hiveview.base.common.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * @author hiveview
 * @date 2017-10-23 18:14:29
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
public class DemoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称.
     */
    private String name;

    /**
     * 编码.
     */
    private String code;

    /**
     * 价格.
     */
    private BigDecimal price;

    /**
     * 描述.
     */
    private String description;

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of demo_entity.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for demo_entity.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of demo_entity.code
     */
    public String getCode() {
        return code;
    }

    /**
     * {@linkplain #code}
     * @param code the value for demo_entity.code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 
     * {@linkplain #price}
     *
     * @return the value of demo_entity.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * {@linkplain #price}
     * @param price the value for demo_entity.price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 
     * {@linkplain #description}
     *
     * @return the value of demo_entity.description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@linkplain #description}
     * @param description the value for demo_entity.description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}