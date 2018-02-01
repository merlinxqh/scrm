package com.hiveview.pms.service.impl;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.service.impl.BaseCrudServiceImpl;
import com.hiveview.pms.dao.SysUserRoleMapper;
import com.hiveview.pms.entity.sys.SysUserRole;
import com.hiveview.pms.service.SysUserRoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author hiveview
 * @date 2017-11-08 15:45:55
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseCrudServiceImpl<SysUserRole>  implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public CrudMapper init() {
        return sysUserRoleMapper;
    }
}