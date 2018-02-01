package com.hiveview.pms.service;

import com.hiveview.base.service.BaseCrudService;
import com.hiveview.pms.dto.RoleResourceDto;
import com.hiveview.pms.entity.sys.SysRole;

/**
 * sys_role
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
public interface SysRoleService extends BaseCrudService<SysRole> {

    /**
     * 数据 启用禁用
     * @param role
     * @return
     */
    int modifyData(SysRole role);

    void roleResourceSave(RoleResourceDto dto);
}