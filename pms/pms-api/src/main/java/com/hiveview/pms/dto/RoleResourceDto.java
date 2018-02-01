package com.hiveview.pms.dto;

import java.io.Serializable;

/**
 * Created by leo on 2017/12/6.
 * 角色资源 关联数据
 */
public class RoleResourceDto implements Serializable{

    private String roleCode;

    private String resourceCodes;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getResourceCodes() {
        return resourceCodes;
    }

    public void setResourceCodes(String resourceCodes) {
        this.resourceCodes = resourceCodes;
    }
}
