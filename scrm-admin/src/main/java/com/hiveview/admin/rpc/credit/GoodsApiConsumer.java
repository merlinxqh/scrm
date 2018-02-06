package com.hiveview.admin.rpc.credit;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hiveview.common.api.PageDto;
import com.hiveview.credit.api.GoodsApiService;
import com.hiveview.credit.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lijuan on 2018/1/29.
 */
@Component
public class GoodsApiConsumer {

    @Reference(registry = "zookeeperClient")
    private GoodsApiService goodsApiServive;

    /**
     * 查询分页数据
     * @param page
     * @param params
     * @return
     */
    public PageDto<GoodsDto> getPageData(PageDto<GoodsDto> page, GoodsDto params){
        return goodsApiServive.findPage(page,params);
    }

    /**
     * 保存数据
     * @param dto
     * @return
     */
    public int saveData(GoodsDto dto){
        return goodsApiServive.saveData(dto);
    }
}
