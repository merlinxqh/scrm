package com.hiveview.admin.rpc.pms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.PageDto;
import com.hiveview.pms.api.BasicDataApiService;
import com.hiveview.pms.api.BasicDataTypeApiService;
import com.hiveview.pms.dto.BasicDataDto;
import com.hiveview.pms.dto.BasicDataTypeDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by leo on 2018/2/5.
 * 基础数据
 */
@Component
public class BasicDataApiConsumer {

    @Reference(registry = "zookeeperClient")
    private BasicDataApiService basicDataApiService;

    @Reference(registry = "zookeeperClient")
    private BasicDataTypeApiService basicDataTypeApiService;


    public List<BasicDataTypeDto> findTypeList(BasicDataTypeDto dto){
       return basicDataTypeApiService.findList(dto);
    }

    public BasicDataTypeDto findTypeById(long id){
       return basicDataTypeApiService.findById(Long.valueOf(id));
    }

    public BasicDataDto findById(long id){
        return basicDataApiService.findById(id);
    }

    public void moidfyData(ModifyCommonDto dto){
        basicDataApiService.modifyData(dto);
    }

    public PageDto findByPage(PageDto page, BasicDataDto params){
        return basicDataApiService.findPage(page, params);
    }

    public void saveData(BasicDataDto dto){
        basicDataApiService.saveData(dto);
    }

    public void saveTypeData(BasicDataTypeDto dto){
        basicDataTypeApiService.saveData(dto);
    }

}
