package com.hiveview.credit.dto;

import com.hiveview.common.api.BaseEntityDto;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by user on 2017/10/24.
 */
public class ActualGoodsStockDto extends BaseEntityDto{

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

    public BigInteger getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(BigInteger goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getUsedStock() {
        return usedStock;
    }

    public void setUsedStock(Integer usedStock) {
        this.usedStock = usedStock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
