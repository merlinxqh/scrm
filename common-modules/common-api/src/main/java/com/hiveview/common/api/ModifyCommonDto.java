package com.hiveview.common.api;

import com.hiveview.common.api.enums.ModifyTypeEnum;

import java.io.Serializable;

/**
 * Created by leo on 2018/1/10.
 * 通用操作启用,禁用,审核,删除 等操作
 */
public class ModifyCommonDto implements Serializable{

    private ModifyTypeEnum modifyType;//操作类型

    /**
     * id,code二选一
     */
    private String id;

    private String code;

    private String ext;//拓展参数

    public ModifyTypeEnum getModifyType() {
        return modifyType;
    }

    public void setModifyType(ModifyTypeEnum modifyType) {
        this.modifyType = modifyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
