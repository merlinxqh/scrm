package com.hiveview.pms.api;

import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.PageDto;
import com.hiveview.pms.common.WrapperApiService;
import com.hiveview.pms.dto.BasicDataTypeDto;
import com.hiveview.pms.entity.basic.BasicDataType;
import com.hiveview.pms.service.BasicDataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by leo on 2018/1/15.
 */
@Service("basicDataTypeApiService")
public class BasicDataTypeApiServiceImpl implements BasicDataTypeApiService {

    @Autowired
    private BasicDataTypeService basicDataTypeService;

    @Override
    public int saveData(BasicDataTypeDto data) {
        return basicDataTypeService.saveData(ObjectUtils.copyObject(data, BasicDataType.class));
    }

    @Override
    public int deleteData(BasicDataTypeDto data) {
        Assert.isTrue(null != data && StringUtils.hasText(data.getId()), "参数有误");
        return basicDataTypeService.deleteById(Long.valueOf(data.getId()));
    }

    @Override
    public List<BasicDataTypeDto> findList(BasicDataTypeDto params) {
        List<BasicDataType> typeList = basicDataTypeService.findByBiz(ObjectUtils.changeToMap(params));
        if(!CollectionUtils.isEmpty(typeList)){
            return ObjectUtils.copyListObject(typeList,BasicDataTypeDto.class);
        }
        return null;
    }

    @Override
    public BasicDataTypeDto findById(Long id) {
        BasicDataType data = basicDataTypeService.findById(id);
        return ObjectUtils.copyObject(data, BasicDataTypeDto.class);
    }

    @Override
    public PageDto<BasicDataTypeDto> findPage(PageDto<BasicDataTypeDto> page, BasicDataTypeDto params) {
        return WrapperApiService.findByPage(page, params, basicDataTypeService, BasicDataTypeDto.class);
    }
}
