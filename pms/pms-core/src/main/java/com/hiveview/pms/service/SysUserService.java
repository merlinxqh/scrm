package com.hiveview.pms.service;

import com.hiveview.base.service.BaseCrudService;
import com.hiveview.pms.entity.sys.SysUser;

/**
 * sys_user
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
public interface SysUserService extends BaseCrudService<SysUser> {

    void modifyData(SysUser dto);
}