package com.hiveview.credit.api;

import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.PageDto;
import com.hiveview.credit.common.WrapperApiService;
import com.hiveview.credit.dto.ActualGoodsStockDto;
import com.hiveview.credit.dto.GoodsDto;
import com.hiveview.credit.entity.ActualGoodsStock;
import com.hiveview.credit.entity.Goods;
import com.hiveview.credit.service.ActualGoodsStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiveview.credit.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lijuan on 2018/1/29.
 */
@Service
public class GoodsApiServiceImpl implements GoodsApiService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ActualGoodsStockService actualGoodsStockService;

    /**
     * 商品列表数据
     * @param page
     * @param params
     * @return
     */
    @Override
    public PageDto<GoodsDto> findPage(PageDto<GoodsDto> page, GoodsDto params) {
        PageDto<GoodsDto> pageDto= WrapperApiService.findByPage(page, params, goodsService, GoodsDto.class);
        if(!CollectionUtils.isEmpty(pageDto.getRecords())){
            Map<String, Object> param = new HashMap<>();
            param.put("goodsIdList",pageDto.getRecords().stream().map(GoodsDto::getId).collect(Collectors.toList()));
            //查询商品库存
            List<ActualGoodsStock> actualGoodsStock = actualGoodsStockService.findByBiz(param);
            if(!CollectionUtils.isEmpty(actualGoodsStock)){
                pageDto.getRecords().forEach(goods->{
                    actualGoodsStock.stream().filter(act-> String.valueOf(act.getGoodsId()).equals(goods.getId())).forEach(act->{
                        goods.setActualGoodsStockDto(ObjectUtils.copyObject(act, ActualGoodsStockDto.class));
                    });
                });
            }
        }
        return pageDto;
    }

    @Override
    @Transactional
    public int saveData(GoodsDto data) {
        return goodsService.saveData(ObjectUtils.copyObject(data,Goods.class));
    }

    @Override
    public int deleteData(GoodsDto data) {
        return 0;
    }

    @Override
    public List<GoodsDto> findList(GoodsDto params) {
        return null;
    }

    @Override
    public GoodsDto findById(Long id) {
        return null;
    }

    /*@Override
    public int deleteData(GoodsDto data) {
        Assert.isTrue(null != data && StringUtils.hasText(data.getId()), "参数有误");
        return goodsApiServive.deleteById(Long.valueOf(data.getId()));
    }*/
}
