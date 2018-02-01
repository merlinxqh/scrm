package com.hiveview.credit.entity;

import lombok.Data;
import com.hiveview.base.common.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Goods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String goodsName;

    private Boolean goodsType;

    private String goodsSubtitle;

    private String goodsCoverUrl;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private Integer goodsStatus;

    private String goodsAuditDescription;

    private Date goodsShelfTime;

    private Date createTime;

    private Date updateTime;

    private BigDecimal freight;

    private String merchantId;

    private String merchantName;

    private Integer integralRuleId;

    private Integer goodsExchangeIntegral;

    private Boolean sameDeviceLimited;

    private Integer sameDeviceLimitedNumber;

    private Boolean sameUserLimited;

    private Integer sameUserLimitedNumber;

    private Boolean settlementBasisId;

    private Long sequenceNumber;

    private BigDecimal orginalPrice;

    private Long viralCardId;

    private ActualGoodsStock actualGoodsStock;
    //********************************用于搜索******************************
    private Date startTime;
    private Date endTime;

    public String getGoodsName() {
        return goodsName;
    }
}