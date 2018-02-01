package com.hiveview.pms.common;

import com.hiveview.base.mybatis.page.Page;
import com.hiveview.base.service.BaseCrudService;
import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.PageDto;

import java.util.List;

/**
 * Created by leo on 2017/12/6.
 * api接口通用方法 封装
 */
public class WrapperApiService {

    /**
     * 通用分页 转换方法 封装
     * @param page
     * @param params
     * @param inter
     * @param clz
     * @param <D> DTO对象
     * @param <S> 数据库实例 对象
     * @param <I> 对应接口实例
     * @return
     */
    public static <D,S,I extends BaseCrudService<S>> PageDto<D> findByPage(PageDto<D> page, D params, I inter, Class<D> clz){
        Page _page= ObjectUtils.copyObject(page,Page.class);
        inter.findByPage(_page,ObjectUtils.changeToMap(params));
        List<S> list=_page.getRecords();
        page=ObjectUtils.copyObject(_page,PageDto.class);
        page.setRecords(ObjectUtils.copyListObject(list,clz));
        return page;
    }
}
