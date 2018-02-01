package com.hiveview.pms.service.impl;

import com.hiveview.base.common.BaseConstants;
import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.cache.redis.RedisService;
import com.hiveview.common.api.ModifyCommonDto;
import com.hiveview.common.api.enums.ModifyTypeEnum;
import com.hiveview.pms.common.PmsRedisConstants;
import com.hiveview.pms.dao.BasicDataMapper;
import com.hiveview.pms.entity.basic.BasicData;
import com.hiveview.pms.entity.basic.BasicDataType;
import com.hiveview.pms.service.BasicDataService;
import javax.annotation.Resource;

import com.hiveview.pms.service.BasicDataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
@Service("basicDataService")
public class BasicDataServiceImpl extends BaseCrudServiceImpl<BasicData> implements BasicDataService {

    @Resource
    private BasicDataMapper basicDataMapper;

    @Autowired
    private BasicDataTypeService basicDataTypeService;

    @Autowired
    private RedisService redisService;

    @Override
    public CrudMapper init() {
        return basicDataMapper;
    }

    @Override
    @Transactional
    public void modifyData(ModifyCommonDto dto) {
        Assert.notNull(dto,"参数不能为空");
        Assert.isTrue(StringUtils.hasText(dto.getId()),"ID不能为空");
        Assert.isTrue(null != dto.getModifyType());
        BasicData data=this.findById(Long.valueOf(dto.getId()));
        Assert.notNull(data,"ID有误");
        if(ModifyTypeEnum.disable.equals(dto.getModifyType())){
            Assert.isTrue(data.getStatus() == BaseConstants.ENABLE_STATUS,"数据已经被禁用");
            data.setStatus(BaseConstants.DISABLE_STATUS);
            this.basicDataMapper.updateByPrimaryKey(data);
        }else if(ModifyTypeEnum.enable.toString().equals(dto.getModifyType())){
            Assert.isTrue(data.getStatus() == BaseConstants.DISABLE_STATUS,"数据已经启用");
            data.setStatus(1);
            BasicDataType type=this.basicDataTypeService.getByCode(data.getTypeCode());
            if(!type.getMultipleOnline()){//该类型只允许一条数据启用
                Map<String,Object> param=new HashMap<>();
                param.put("typeCode",data.getTypeCode());
                param.put("status",BaseConstants.ENABLE_STATUS);
                int count=basicDataMapper.selectCount(param);
                Assert.isTrue(count == 0,"只允许启用一条数据,请先禁用已启用数据");
            }
            this.basicDataMapper.updateByPrimaryKey(data);
        }
        operateBasicDataToCache(data);
    }

    @Override
    @Transactional
    public int saveData(BasicData entity) throws ServiceException {
        int count=0;
        Assert.notNull(entity,"参数不能为空");
        if(null == entity.getId()){
            //校验
            Assert.hasText(entity.getTypeCode(),"类型编码不能为空");
            BasicDataType type=basicDataTypeService.getByCode(entity.getTypeCode());
            Assert.notNull(type,"类型编码有误");
            entity.setTypeLongCode(type.getLongCode());
            entity.setStatus(BaseConstants.DISABLE_STATUS);//默认失效
            entity.setDelFlag(BaseConstants.UN_DELETE_FLAG);//默认未删除
            count=super.saveData(entity);
        }else{
            BasicData old=this.findById(entity.getId());
            Assert.notNull(old);
            count=basicDataMapper.updateByPrimaryKeySelective(entity);
            operateBasicDataToCache(old);
        }
        return count;
    }

    @Override
    public List<BasicData> getByTypeCode(String typeCode) {
        if(redisService.mapExists(PmsRedisConstants.PMS_BASIC_DATA_MAP,typeCode)){
            List<BasicData> dataList=redisService.getHashListObj(PmsRedisConstants.PMS_BASIC_DATA_MAP, typeCode, BasicData.class);
            return dataList;
        }else{
            List<BasicData> dataList = getEnableData(typeCode);
            if(!CollectionUtils.isEmpty(dataList)){
                redisService.setHashListObj(PmsRedisConstants.PMS_BASIC_DATA_MAP, typeCode, dataList, 0);
                return dataList;
            }
        }
        return null;
    }

    /**
     * 基础数据缓存操作
     * @param data
     */
    public void operateBasicDataToCache(BasicData data){
        List<BasicData> list = getEnableData(data.getTypeCode());
        if(redisService.mapExists(PmsRedisConstants.PMS_BASIC_DATA_MAP,data.getTypeCode())){
            redisService.mapRemove(PmsRedisConstants.PMS_BASIC_DATA_MAP,data.getTypeCode());
        }
        redisService.setHashListObj(PmsRedisConstants.PMS_BASIC_DATA_MAP,data.getTypeCode(),list,0);
    }

    /**
     * 获取有效的 基础数据
     * @param typeCode
     * @return
     */
    public List<BasicData> getEnableData(String typeCode){
        Map<String,Object> map = new HashMap<>();
        map.put("typeCode", typeCode);
        map.put("status", BaseConstants.ENABLE_STATUS);
        List<BasicData> list=findByBiz(map);
        return list;
    }
}