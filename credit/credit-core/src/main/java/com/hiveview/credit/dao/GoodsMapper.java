package com.hiveview.credit.dao;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.mybatis.annotation.MyBatisDao;
import com.hiveview.credit.entity.Goods;

@MyBatisDao
public interface GoodsMapper extends CrudMapper {
    Goods getGoodsById(Goods goods);
    int getMaxSeq();
}