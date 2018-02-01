package com.hiveview.credit.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.credit.dao.GoodsMapper;
import com.hiveview.credit.entity.Goods;
import com.hiveview.credit.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Created by lijuan on 2018/1/29.
 */
@Service("goodsService")
public class GoodsServiceImpl extends BaseCrudServiceImpl<Goods> implements GoodsService{
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public CrudMapper init() {
        return goodsMapper;
    }

    @Override
    @Transactional
    public int saveData(Goods entity) throws ServiceException {
        Assert.notNull(entity,"参数不能为空");
        if(null != entity.getId()){
            
        }
        return 0;
    }
}
