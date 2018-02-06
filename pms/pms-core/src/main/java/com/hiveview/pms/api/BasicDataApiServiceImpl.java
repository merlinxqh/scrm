package com.hiveview.pms.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.PageDto;
import com.hiveview.pms.common.WrapperApiService;
import com.hiveview.pms.dto.BasicDataDto;
import com.hiveview.pms.entity.basic.BasicData;
import com.hiveview.pms.service.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by leo on 2018/1/15.
 */
@Service(registry = "zookeeperService")
public class BasicDataApiServiceImpl implements BasicDataApiService {

    @Autowired
    private BasicDataService basicDataService;

    @Override
    @Transactional
    public int saveData(BasicDataDto data) {
        return basicDataService.saveData(ObjectUtils.copyObject(data, BasicData.class));
    }

    @Override
    @Transactional
    public int deleteData(BasicDataDto data) {
        Assert.isTrue(null != data && StringUtils.hasText(data.getId()),"参数有误");
        return basicDataService.deleteById(Long.valueOf(data.getId()));
    }

    @Override
    public List<BasicDataDto> findList(BasicDataDto params) {
        List<BasicData> list = basicDataService.findByBiz(ObjectUtils.changeToMap(params));
        return ObjectUtils.copyListObject(list, BasicDataDto.class);
    }

    @Override
    public BasicDataDto findById(Long id) {
        BasicData data = basicDataService.findById(id);
        return ObjectUtils.copyObject(data, BasicDataDto.class);
    }

    @Override
    @Transactional
    public void modifyData(ModifyCommonDto dto) {
        basicDataService.modifyData(dto);
    }

    @Override
    public PageDto<BasicDataDto> findPage(PageDto<BasicDataDto> page, BasicDataDto params) {
        return WrapperApiService.findByPage(page, params, basicDataService, BasicDataDto.class);
    }
}
