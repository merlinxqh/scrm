package com.hiveview.pms.dto;

import com.hiveview.common.api.BaseEntityDto;

/**
 * Created by leo on 2017/10/24.
 * 系统角色
 */
public class SysRoleDto extends BaseEntityDto{

    /**
     * 编码.
     */
    private String code;

    /**
     * 名称.
     */
    private String name;

    /**
     * 状态(1:启用,2:禁用).
     */
    private Integer status;

    /**
     * 角色类型.
     */
    private String roleType;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 排除角色类型
     */
    private String exceptRoleType;


    public String getExceptRoleType() {
        return exceptRoleType;
    }

    public void setExceptRoleType(String exceptRoleType) {
        this.exceptRoleType = exceptRoleType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
