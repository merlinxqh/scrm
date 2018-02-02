package com.hiveview.base.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leo on 2017/11/3.
 * 基类
 */
@Data
public class BaseEntity  implements Serializable {

    private static final long serialVersionUID = 5557663618469589569L;

    private Long id;

    private String createBy;

    private Date createDate;

    private String lastUpdateBy;

    private Date lastUpdateDate;
}
