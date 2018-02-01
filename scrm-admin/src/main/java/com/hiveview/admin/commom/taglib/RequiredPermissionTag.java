package com.hiveview.admin.commom.taglib;


import com.hiveview.admin.commom.SystemUserUtils;

import javax.servlet.jsp.JspTagException;

/**
 * Created by leo on 2017/6/1.
 * 权限标签
 */
public class RequiredPermissionTag extends AbstractPermissionTag {

    @Override
    protected boolean condition() throws JspTagException {
        return SystemUserUtils.hasPermission(getResource());
    }
}
