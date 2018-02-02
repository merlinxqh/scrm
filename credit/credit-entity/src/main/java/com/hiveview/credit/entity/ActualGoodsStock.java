package com.hiveview.credit.entity;

import com.hiveview.base.common.BaseEntity;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by user on 2017/10/24.
 */
@Data
public class ActualGoodsStock  extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private BigInteger goodsId;

    /**
     * 商品剩余库存量
     */
    private Integer stock;

    /**
     * 商品消耗库存量
     */
    private Integer usedStock;

    /**
     * 商品锁定库存量
     */
    private Integer lockStock;

    /**
     * 库存创建时间
     */
    private Date createTime;

    /**
     * 库存更新时间
     */
    private Date updateTime;
}
