/**
 * @(#)Order.java 1.0 2016-6-16
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long orderId;
    private String code;
    private Timestamp tradeTime;
    private int count;
    // 不参与计算，故这里使用double
    private double orderAmount;
    private double platformIncome;
    private double customerIncome;
    private double treadFee;
    private byte platform;

    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Timestamp tradeTime) {
        this.tradeTime = tradeTime;
    }

    public byte getPlatform() {
        return platform;
    }

    public void setPlatform(byte platform) {
        this.platform = platform;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getPlatformIncome() {
        return platformIncome;
    }

    public void setPlatformIncome(double platformIncome) {
        this.platformIncome = platformIncome;
    }

    public double getCustomerIncome() {
        return customerIncome;
    }

    public void setCustomerIncome(double customerIncome) {
        this.customerIncome = customerIncome;
    }

    public double getTreadFee() {
        return treadFee;
    }

    public void setTreadFee(double treadFee) {
        this.treadFee = treadFee;
    }

}
