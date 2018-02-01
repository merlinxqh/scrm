package com.hiveview.credit.dto;

import com.hiveview.common.api.BaseEntityDto;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsDto extends BaseEntityDto {

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

    private ActualGoodsStockDto actualGoodsStockDto;
    //********************************用于搜索******************************
    private Date startTime;
    private Date endTime;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Boolean getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Boolean goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsSubtitle() {
        return goodsSubtitle;
    }

    public void setGoodsSubtitle(String goodsSubtitle) {
        this.goodsSubtitle = goodsSubtitle;
    }

    public String getGoodsCoverUrl() {
        return goodsCoverUrl;
    }

    public void setGoodsCoverUrl(String goodsCoverUrl) {
        this.goodsCoverUrl = goodsCoverUrl;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsAuditDescription() {
        return goodsAuditDescription;
    }

    public void setGoodsAuditDescription(String goodsAuditDescription) {
        this.goodsAuditDescription = goodsAuditDescription;
    }

    public Date getGoodsShelfTime() {
        return goodsShelfTime;
    }

    public void setGoodsShelfTime(Date goodsShelfTime) {
        this.goodsShelfTime = goodsShelfTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getIntegralRuleId() {
        return integralRuleId;
    }

    public void setIntegralRuleId(Integer integralRuleId) {
        this.integralRuleId = integralRuleId;
    }

    public Integer getGoodsExchangeIntegral() {
        return goodsExchangeIntegral;
    }

    public void setGoodsExchangeIntegral(Integer goodsExchangeIntegral) {
        this.goodsExchangeIntegral = goodsExchangeIntegral;
    }

    public Boolean getSameDeviceLimited() {
        return sameDeviceLimited;
    }

    public void setSameDeviceLimited(Boolean sameDeviceLimited) {
        this.sameDeviceLimited = sameDeviceLimited;
    }

    public Integer getSameDeviceLimitedNumber() {
        return sameDeviceLimitedNumber;
    }

    public void setSameDeviceLimitedNumber(Integer sameDeviceLimitedNumber) {
        this.sameDeviceLimitedNumber = sameDeviceLimitedNumber;
    }

    public Boolean getSameUserLimited() {
        return sameUserLimited;
    }

    public void setSameUserLimited(Boolean sameUserLimited) {
        this.sameUserLimited = sameUserLimited;
    }

    public Integer getSameUserLimitedNumber() {
        return sameUserLimitedNumber;
    }

    public void setSameUserLimitedNumber(Integer sameUserLimitedNumber) {
        this.sameUserLimitedNumber = sameUserLimitedNumber;
    }

    public Boolean getSettlementBasisId() {
        return settlementBasisId;
    }

    public void setSettlementBasisId(Boolean settlementBasisId) {
        this.settlementBasisId = settlementBasisId;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public BigDecimal getOrginalPrice() {
        return orginalPrice;
    }

    public void setOrginalPrice(BigDecimal orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    public Long getViralCardId() {
        return viralCardId;
    }

    public void setViralCardId(Long viralCardId) {
        this.viralCardId = viralCardId;
    }

    public ActualGoodsStockDto getActualGoodsStockDto() {
        return actualGoodsStockDto;
    }

    public void setActualGoodsStockDto(ActualGoodsStockDto actualGoodsStockDto) {
        this.actualGoodsStockDto = actualGoodsStockDto;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}