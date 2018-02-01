package com.hiveview.base.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by leo on 2018/1/15.
 * 树结构 对应实例 要包含以下字段
 */
@Data
public class TreeEntity implements Serializable {

    private String id;

    private String name;

    private String code;

    private String longCode;

    private String parentCode;

    private Integer level;
}
