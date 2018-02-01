package com.hiveview.pms.dao;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.mybatis.annotation.MyBatisDao;

/**
 * sys_role_resource
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@MyBatisDao
public interface SysRoleResourceMapper extends CrudMapper {

    int deleteByRoleCode(String roleCode);
}