package com.hiveview.pms.entity.sys;

import com.hiveview.base.common.BaseEntity;

/**
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
public class SubSystem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系统编码.
     */
    private String code;

    /**
     * 子系统名称.
     */
    private String name;

    /**
     * 项目名.
     */
    private String projectName;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of sub_system.code
     */
    public String getCode() {
        return code;
    }

    /**
     * {@linkplain #code}
     * @param code the value for sub_system.code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of sub_system.name
     */
    public String getName() {
        return name;
    }

    /**
     * {@linkplain #name}
     * @param name the value for sub_system.name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * {@linkplain #projectName}
     *
     * @return the value of sub_system.project_name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * {@linkplain #projectName}
     * @param projectName the value for sub_system.project_name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of sub_system.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * {@linkplain #remark}
     * @param remark the value for sub_system.remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}