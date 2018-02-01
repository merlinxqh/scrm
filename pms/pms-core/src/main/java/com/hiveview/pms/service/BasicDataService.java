package com.hiveview.pms.service;

import com.hiveview.base.service.BaseCrudService;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.pms.entity.basic.BasicData;

import java.util.List;

/**
 * basic_data
 * 
 * @author hiveview
 * @date 2018-01-15 17:01:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
public interface BasicDataService extends BaseCrudService<BasicData> {

    void modifyData(ModifyCommonDto dto);

    /**
     * 根据 类型编码 获取数据
     * @param typeCode
     * @return
     */
    List<BasicData> getByTypeCode(String typeCode);
}