package org.lucius.framework.utils.export;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账单详情实体
 * 
 * @author ISSUSER
 * 
 */
public class ReconciliationDetail {
    /**
     * 交易类型 1商品销售，2书店会员费，3退货
     */
    private int dealType;

    /**
     * 订单号或退货单号
     */
    private String orderCode;

    /**
     * 交易时间
     */
    private Date dealDate;

    /**
     * 交易商品ID
     */
    private Long goodsId;

    /**
     * 交易商品名称
     */
    private String goodsName;

    /**
     * 套餐商品子商品ID
     */
    private Long subGoodsId;

    /**
     * 交易商品名称(套餐商品子商品名称)
     */
    private String subGoodsName;

    /**
     * 交易商品数量
     */
    private int goodsCount;

    /**
     * 交易金额
     */
    private BigDecimal dealMoney;

    /**
     * 平台分成比率
     */
    private BigDecimal platformPercent;

    /**
     * 平台分成金额
     */
    private BigDecimal platformAmount;

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public BigDecimal getPlatformPercent() {
        return platformPercent;
    }

    public void setPlatformPercent(BigDecimal platformPercent) {
        this.platformPercent = platformPercent;
    }

    public BigDecimal getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(BigDecimal platformAmount) {
        this.platformAmount = platformAmount;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSubGoodsId() {
        return subGoodsId;
    }

    public void setSubGoodsId(Long subGoodsId) {
        this.subGoodsId = subGoodsId;
    }

    public String getSubGoodsName() {
        return subGoodsName;
    }

    public void setSubGoodsName(String subGoodsName) {
        this.subGoodsName = subGoodsName;
    }

}
