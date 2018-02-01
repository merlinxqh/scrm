package com.hiveview.pms.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.base.util.id.IdWorker;
import com.hiveview.pms.dao.SysResourceMapper;
import com.hiveview.pms.entity.sys.SysResource;
import com.hiveview.pms.service.SysResourceService;
import javax.annotation.Resource;
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
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("sysResourceService")
public class SysResourceServiceImpl extends BaseCrudServiceImpl<SysResource> implements SysResourceService {

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Override
    public CrudMapper init() {
        return sysResourceMapper;
    }

    @Override
    @Transactional
    public int saveData(SysResource entity) throws ServiceException {
        Assert.notNull(entity, "参数不能为空");
        Assert.notNull(entity.getIsMenu(),"是否为菜单参数不能为空");
        if(null != entity.getId()){

            //修改数据
            SysResource old=this.findById(entity.getId());
            Assert.notNull(old,"找不到资源数据");
            old.setName(entity.getName());
            old.setOrders(entity.getOrders());
            old.setUrl(entity.getUrl());
            old.setIconCode(entity.getIconCode());
            old.setRemark(entity.getRemark());
            old.setPermission(entity.getPermission());
            return this.sysResourceMapper.updateByPrimaryKey(old);
        }else{
            //新增
            entity.setCode(IdWorker.getStringCode());
            entity.setStatus(1);
            if(!StringUtils.isEmpty(entity.getParentCode())){
                //有上级资源
                Map<String,Object> param=new HashMap<>();
                param.put("code",entity.getParentCode());
                List<SysResource> plist=this.findByBiz(param);
                Assert.isTrue(!CollectionUtils.isEmpty(plist),"上级资源编码有误");
                SysResource parent=plist.get(0);
                if(entity.getIsMenu() == 1){//如果是添加菜单数据
                    Assert.isTrue(parent.getLevel() == 1,"菜单只能添加两级");//目前只允许添加两级菜单
                }
                entity.setLongCode(parent.getLongCode()+","+entity.getCode());
                entity.setLevel(parent.getLevel()+1);
            }else{
                entity.setLevel(1);//没有上级 就是一级菜单
                entity.setLongCode(entity.getCode());
            }
            return sysResourceMapper.insert(entity);
        }
    }

    @Override
    @Transactional
    public int deleteById(Long id) throws ServiceException {
        int childCount=sysResourceMapper.checkChildCount(id);
        Assert.isTrue(childCount==0,"包含下级数据不能删除");
        return sysResourceMapper.deleteByPrimaryKey(id);
    }
}