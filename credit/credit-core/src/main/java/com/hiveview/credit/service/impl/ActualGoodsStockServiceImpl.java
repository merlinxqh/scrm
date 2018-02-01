package com.hiveview.credit.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.credit.dao.ActualGoodsStockMapper;
import com.hiveview.credit.entity.ActualGoodsStock;
import com.hiveview.credit.service.ActualGoodsStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lijuan on 2018/1/29.
 */
@Service("actualGoodsStockService")
public class ActualGoodsStockServiceImpl extends BaseCrudServiceImpl<ActualGoodsStock> implements ActualGoodsStockService{

    @Resource
    private ActualGoodsStockMapper actualGoodsStockMapper;

    @Override
    public CrudMapper init() {
        return actualGoodsStockMapper;
    }

    @Override
    public void getGoodsStock(List<ActualGoodsStock> records){

    }
}
