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
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;

    /**
     * 名称.
     */
    private String name;

    /**
     * 状态(1:启用,2:禁用).
     */
    private Integer status;

    /**
     * 邮箱.
     */
    private String email;

    /**
     * 默认角色
     */
    private SysRole defaultRole;
}