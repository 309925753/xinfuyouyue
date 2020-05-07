package com.xfyyim.cn.bean;

/**
 * 订单详情
 */
public class PayOrderBean {
    private String money;// 订单金额

    private Integer userId;// 用户

    private String appId;// 应用id

    private Integer appType;// 商品type

    private byte status;// 订单状态  0：创建  1：支付完成  2：交易失败

    private long createTime;// 创建时间

    private String desc;// 商品说明

    // private String sign;// 订单签名

    private String callBackUrl;// 回调路径

    private String IPAdress;// 请求ip地址

    private String trade_no;// 用户生成订单号

    private String level; // 级别

    private Integer payType; // 付款类型

    private Integer quantity; // 购买数量

    private String ali_tarde_no; // 支付宝交易号

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    private String serviceCharge; // 手续费

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getIPAdress() {
        return IPAdress;
    }

    public void setIPAdress(String IPAdress) {
        this.IPAdress = IPAdress;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAli_tarde_no() {
        return ali_tarde_no;
    }

    public void setAli_tarde_no(String ali_tarde_no) {
        this.ali_tarde_no = ali_tarde_no;
    }

}
