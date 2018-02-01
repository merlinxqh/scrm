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
public class SysRoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码.
     */
    private String roleCode;

    /**
     * 资源编码.
     */
    private String resourceCode;
}