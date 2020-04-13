package com.xfyyim.cn.bean;

import java.io.Serializable;

public class BillCashBean implements Serializable, Cloneable  {


    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getPaymenType() {
        return paymenType;
    }

    public void setPaymenType(String paymenType) {
        this.paymenType = paymenType;
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType;
    }

    public BillCashBean(String billTime, String billMoney, String paymenType, String consumptionType, String billName) {
        this.billTime = billTime;
        this.billMoney = billMoney;
        this.paymenType = paymenType;
        this.consumptionType = consumptionType;
        this.billName = billName;
    }

    private String  billTime;
    private String  billMoney;
    private String  paymenType;
    private String consumptionType;
    private String  billName;

}
