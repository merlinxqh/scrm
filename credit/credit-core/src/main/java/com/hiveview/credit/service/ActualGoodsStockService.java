package com.hiveview.credit.service;

import com.hiveview.base.service.BaseCrudService;
import com.hiveview.credit.entity.ActualGoodsStock;

import java.util.List;
import java.util.Map;

/**
 * Created by lijuan on 2018/1/29.
 */
public interface ActualGoodsStockService extends BaseCrudService<ActualGoodsStock> {
    /**
     * 获取商品库存
     * @param records
     * @return
     */
    void getGoodsStock(List<ActualGoodsStock> records);
}
