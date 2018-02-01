package com.hiveview.pms.entity.sys;

import com.hiveview.base.common.BaseEntity;
import lombok.Data;

/**
 * 
 * @author hiveview
 * @date 2017-11-03 17:24:06
 * @version 1.0.0
 * @copyright www.hiveview.com
 */
@Data
public class SysResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编码.
     */
    private String code;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 长编码.
     */
    private String longCode;

    /**
     * 状态(1:启用,2:禁用).
     */
    private Integer status;

    /**
     * 关联子系统.
     */
    private String subSystemCode;

    /**
     * 父编码.
     */
    private String parentCode;

    /**
     * 访问路径.
     */
    private String url;

    /**
     * 1:菜单,2:权限项.
     */
    private Integer isMenu;

    /**
     * 排序字段.
     */
    private Integer orders;

    /**
     * 级别.
     */
    private Integer level;

    /**
     * 菜单样式.
     */
    private String iconCode;

    /**
     * 权限标识.
     */
    private String permission;

    /**
     * 备注
     */
    private String remark;
}