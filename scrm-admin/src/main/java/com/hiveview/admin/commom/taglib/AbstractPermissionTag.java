package com.hiveview.admin.commom.taglib;

import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * Created by leo on 2017/6/1.
 */
public abstract class AbstractPermissionTag extends ConditionalTagSupport {

    /**
     * 资源权限标识
     */
    private String resource;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
