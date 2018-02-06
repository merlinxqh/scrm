package com.hiveview.credit.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.hiveview.common.api.PageDto;
import com.hiveview.credit.common.WrapperApiService;
import com.hiveview.credit.dto.ActualGoodsStockDto;
import com.hiveview.credit.service.ActualGoodsStockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lijuan on 2018/1/29.
 */
@Service(registry = "zookeeperService")
public class ActualGoodsStockApiServiceImpl implements ActualGoodsStockApiService {
    @Autowired
    private ActualGoodsStockService actualGoodsStockService;

    @Override
    public int saveData(ActualGoodsStockDto data) {
        return 0;
    }

    @Override
    public int deleteData(ActualGoodsStockDto data) {
        return 0;
    }

    @Override
    public List<ActualGoodsStockDto> findList(ActualGoodsStockDto params) {
        return null;
    }

    @Override
    public ActualGoodsStockDto findById(Long id) {
        return null;
    }

    @Override
    public PageDto<ActualGoodsStockDto> findPage(PageDto<ActualGoodsStockDto> page, ActualGoodsStockDto params) {
        return WrapperApiService.findByPage(page, params, actualGoodsStockService, ActualGoodsStockDto.class);
    }
}
