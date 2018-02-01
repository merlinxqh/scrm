package com.hiveview.pms.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.pms.dao.BasicDataTypeMapper;
import com.hiveview.pms.entity.basic.BasicDataType;
import com.hiveview.pms.service.BasicDataTypeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author hiveview
 * @date 2018-01-15 17:01:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("basicDataTypeService")
public class BasicDataTypeServiceImpl extends BaseCrudServiceImpl<BasicDataType> implements BasicDataTypeService {

    @Resource
    private BasicDataTypeMapper basicDataTypeMapper;

    @Override
    public CrudMapper init() {
        return basicDataTypeMapper;
    }

    @Override
    public BasicDataType getByCode(String code) {
        if(StringUtils.hasText(code)){
            Map<String,Object> map = new HashMap<>();
            map.put("code", code);
            BasicDataType data = this.findOneByBiz(map);
            return data;
        }
        return null;
    }

    @Override
    @Transactional
    public int saveData(BasicDataType entity) throws ServiceException {
        Assert.notNull(entity);
        if(null == entity.getMultipleOnline()){
            entity.setMultipleOnline(true);//默认允许多条数据启用
        }
        if(null == entity.getId()){
            Assert.hasText(entity.getCode(),"编码不能为空");
            int check=getCountByCode(entity.getCode());
            Assert.isTrue(check == 0,"编码不唯一");
            if(!StringUtils.isEmpty(entity.getParentCode())){
                Map<String,Object> map=new HashMap<>();
                map.put("code",entity.getParentCode());
                BasicDataType parent=findOneByBiz(map);
                Assert.notNull(parent,"上级编码有误");
                entity.setLevel(parent.getLevel()+1);
                entity.setLongCode(parent.getLongCode()+","+entity.getCode());
            }else{//一级数据
                entity.setLevel(1);
                entity.setLongCode(entity.getCode());
            }
            return basicDataTypeMapper.insert(entity);
        }else{
            BasicDataType old=this.findById(entity.getId());
            Assert.notNull(old);
            return basicDataTypeMapper.updateByPrimaryKeySelective(entity);
        }
    }

    public int getCountByCode(String code){
        Map<String,Object> map=new HashMap<>();
        map.put("code", code);
        int count = basicDataTypeMapper.selectCount(map);
        return count;
    }
}