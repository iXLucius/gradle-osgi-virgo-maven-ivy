/**
 * @(#)OrderItem.java 1.0 2016-6-16
 * @Copyright:  Copyright 2007 - 2016 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2016-6-16
 * Author:      lucius.lv
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.export;

/**
 * 订单实体
 * @author wanghongye
 * @Date:2016-8-10
 */
public class OrderItem {

    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 交易商品名称
     */
    private String goodsName;
    
    /**
     * 单价（元）
     */
    private double price;
    
    /**
     * 商品总价（元）
     */
    private double totalAmount;
    /**
     * 交易数量（个）
     */
    private int num;
    
    private double platIncome;
    
    /**
     * 我的收入（元
     */
    private double customerIncome;
    private double tradeFee;
    /**
     * 分成比
     */
    private double percent;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPlatIncome() {
        return platIncome;
    }

    public void setPlatIncome(double platIncome) {
        this.platIncome = platIncome;
    }

    public double getCustomerIncome() {
        return customerIncome;
    }

    public void setCustomerIncome(double customerIncome) {
        this.customerIncome = customerIncome;
    }

    public double getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(double tradeFee) {
        this.tradeFee = tradeFee;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

}
