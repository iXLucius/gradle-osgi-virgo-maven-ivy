package org.lucius.framework.utils.export;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账单实体
 * @author wanghongye
 * @Date:2016-8-10
 */
public class Reconciliation {

    /**
     * 结算单ID
     */
    private String reconciliationId;

    /**
     * 结算单号
     */
    private String reconciliationCode;
    /**
     * 对账开始时间
     */
    private Date reconciliationStartTime;

    /**
     * 对账结束时间
     */
    private Date reconciliationEndTime;

    /**
     * 付款方
     */
    private String payer;

    /**
     * 付款方账号
     */
    private String payAccount;

    /**
     * 收入方
     */
    private String incomer;

    /**
     * 对账方店铺名称
     */
    private String reconciliationShopName;

    /**
     * 对账方店铺ID
     */
    private Long reconciliationShopId;

    /**
     * 对账总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单对账金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单归属平台分成金额(元)
     */
    private BigDecimal reconciliationOrderAmount;

    /**
     * 会员缴费金额(元)
     */
    private BigDecimal vipAmount;

    /**
     * 商品总数
     */
    private int goodsCount;

    /**
     * 订单总数
     */
    private int orderCount;

    /**
     * 出版社收入
     */
    private double publisherAmount;

    /**
     * 泛媒收入
     */
    private double platformAmount;

    private double feeAmount;

    /**
     * 会员缴费归属平台分成金额(元)
     */
    private BigDecimal reconciliationVIPAmount;

    /**
     * 结算状态 0:结算失败 ，1：待结算，2：结算已审核，3结算成功
     */
    private byte status;
    
    /**
     * 订单号
     */
    private String ordercode;
    /**
     * 交易时间
     */
    private Date tradeTime;
    
    public Date getReconciliationStartTime() {
        return reconciliationStartTime;
    }

    public void setReconciliationStartTime(Date reconciliationStartTime) {
        this.reconciliationStartTime = reconciliationStartTime;
    }

    public Date getReconciliationEndTime() {
        return reconciliationEndTime;
    }

    public void setReconciliationEndTime(Date reconciliationEndTime) {
        this.reconciliationEndTime = reconciliationEndTime;
    }

    public String getReconciliationShopName() {
        return reconciliationShopName;
    }

    public void setReconciliationShopName(String reconciliationShopName) {
        this.reconciliationShopName = reconciliationShopName;
    }

    public Long getReconciliationShopId() {
        return reconciliationShopId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setReconciliationShopId(Long reconciliationShopId) {
        this.reconciliationShopId = reconciliationShopId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getReconciliationOrderAmount() {
        return reconciliationOrderAmount;
    }

    public void setReconciliationOrderAmount(
            BigDecimal reconciliationOrderAmount) {
        this.reconciliationOrderAmount = reconciliationOrderAmount;
    }

    public BigDecimal getReconciliationVIPAmount() {
        return reconciliationVIPAmount;
    }

    public void setReconciliationVIPAmount(BigDecimal reconciliationVIPAmount) {
        this.reconciliationVIPAmount = reconciliationVIPAmount;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getReconciliationId() {
        return reconciliationId;
    }

    public void setReconciliationId(String reconciliationId) {
        this.reconciliationId = reconciliationId;
    }

    public String getReconciliationCode() {
        return reconciliationCode;
    }

    public void setReconciliationCode(String reconciliationCode) {
        this.reconciliationCode = reconciliationCode;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getIncomer() {
        return incomer;
    }

    public void setIncomer(String incomer) {
        this.incomer = incomer;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getPublisherAmount() {
        return publisherAmount;
    }

    public void setPublisherAmount(double publisherAmount) {
        this.publisherAmount = publisherAmount;
    }

    public double getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(double platformAmount) {
        this.platformAmount = platformAmount;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getVipAmount() {
        return vipAmount;
    }

    public void setVipAmount(BigDecimal vipAmount) {
        this.vipAmount = vipAmount;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

}
