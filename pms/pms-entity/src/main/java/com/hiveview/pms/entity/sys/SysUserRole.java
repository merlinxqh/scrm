package com.hiveview.pms.entity.sys;

import com.hiveview.base.common.BaseEntity;

/**
 * 
 * @author hiveview
 * @date 2017-11-08 15:45:55
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号.
     */
    private String username;

    /**
     * 角色编码.
     */
    private String roleCode;

    /**
     * 
     * {@linkplain #username}
     *
     * @return the value of sys_user_role.username
     */
    public String getUsername() {
        return username;
    }

    /**
     * {@linkplain #username}
     * @param username the value for sys_user_role.username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 
     * {@linkplain #roleCode}
     *
     * @return the value of sys_user_role.role_code
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * {@linkplain #roleCode}
     * @param roleCode the value for sys_user_role.role_code
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }
}