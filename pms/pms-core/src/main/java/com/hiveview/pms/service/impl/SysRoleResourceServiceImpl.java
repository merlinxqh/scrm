package com.hiveview.pms.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.pms.dao.SysRoleResourceMapper;
import com.hiveview.pms.entity.sys.SysRoleResource;
import com.hiveview.pms.service.SysRoleResourceService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("sysRoleResourceService")
public class SysRoleResourceServiceImpl extends BaseCrudServiceImpl<SysRoleResource> implements SysRoleResourceService {

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public CrudMapper init() {
        return sysRoleResourceMapper;
    }

    @Override
    @Transactional
    public int deleteByRoleCode(String roleCode) {
        return sysRoleResourceMapper.deleteByRoleCode(roleCode);
    }
}