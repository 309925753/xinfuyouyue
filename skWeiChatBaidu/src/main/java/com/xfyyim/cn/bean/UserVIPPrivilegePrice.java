package com.xfyyim.cn.bean;

public class UserVIPPrivilegePrice {
    /**
     * 特权
     */
    // VIP会员
   //("普通会员：按月购买")
    private String v0;

    @Override
    public String toString() {
        return "UserVIPPrivilegePrice{" +
                "v0='" + v0 + '\'' +
                ", v0Price=" + v0Price +
                ", v0Quantity=" + v0Quantity +
                ", v0DayPrice=" + v0DayPrice +
                ", v1='" + v1 + '\'' +
                ", v1Price=" + v1Price +
                ", v1Quantity=" + v1Quantity +
                ", v1DayPrice=" + v1DayPrice +
                ", v2='" + v2 + '\'' +
                ", v2Price=" + v2Price +
                ", v2Quantity=" + v2Quantity +
                ", v2DayPrice=" + v2DayPrice +
                ", v3='" + v3 + '\'' +
                ", v3Price=" + v3Price +
                ", v3Quantity=" + v3Quantity +
                ", v3DayPrice=" + v3DayPrice +
                ", likePrivilegePrice1=" + likePrivilegePrice1 +
                ", likePrivilegeDayPrice1=" + likePrivilegeDayPrice1 +
                ", likePrivilegePrice2=" + likePrivilegePrice2 +
                ", likePrivilegeDayPrice2=" + likePrivilegeDayPrice2 +
                ", likePrivilegePrice3=" + likePrivilegePrice3 +
                ", likePrivilegeDayPrice3=" + likePrivilegeDayPrice3 +
                ", chatByMonthPrice1=" + chatByMonthPrice1 +
                ", chatByMonthPrice1ForDay=" + chatByMonthPrice1ForDay +
                ", chatByMonthPrice2=" + chatByMonthPrice2 +
                ", chatByMonthPrice2ForDay=" + chatByMonthPrice2ForDay +
                ", chatByMonthPrice3=" + chatByMonthPrice3 +
                ", chatByMonthPrice3ForDay=" + chatByMonthPrice3ForDay +
                ", chatByFrequency1=" + chatByFrequency1 +
                ", chatByFrequency2=" + chatByFrequency2 +
                ", chatByFrequency10=" + chatByFrequency10 +
                ", chatByFrequency10More=" + chatByFrequency10More +
                ", superLikeByFrequency1=" + superLikeByFrequency1 +
                ", superLikeByFrequency2=" + superLikeByFrequency2 +
                ", superLikeByFrequency10=" + superLikeByFrequency10 +
                ", superLikeByFrequency10More=" + superLikeByFrequency10More +
                ", chatPeekByFrequency1=" + chatPeekByFrequency1 +
                ", chatPeekByFrequency2=" + chatPeekByFrequency2 +
                ", chatPeekByFrequency10=" + chatPeekByFrequency10 +
                ", chatPeekByFrequency10More=" + chatPeekByFrequency10More +
                ", outPrice=" + outPrice +
                ", outMinute=" + outMinute +
                '}';
    }

    //("普通会员：按月购买价格")
    private int v0Price;
   //("普通会员：每天所送次数(点超级喜欢)")
    private int v0Quantity;
    // 普通会员每天价格

    private int v0DayPrice;
    //("年费一级会员：按年购买")
    private String v1;//
    //("年费一级会员：按年购买价格")
    private int v1Price;
    //("年费一级会员：每天所送次数(点超级喜欢)")
    private int v1Quantity;
    // 年费一级会员每天价格

    private int v1DayPrice;
    //("年费二级会员：按年购买")
    private String v2;//
    //("年费二级会员：按年购买价格")
    private int v2Price;
    //("年费二级会员：每天所送次数(点超级喜欢)")
    private int v2Quantity;
    // 年费二级会员每天价格

    private int v2DayPrice;
    //("年费三级会员：按年购买")
    private String v3;//
    //("年费三级会员：按年购买价格")
    private int v3Price;
    //("年费三级会员：每天所送次数(点超级喜欢)")
    private int v3Quantity;
    // 年费三级会员每天价格

    private int v3DayPrice;
    // 查看谁喜欢我
    //("查看喜欢我特权：按每月购买价格")
    private int likePrivilegePrice1;
    // 按每月购买，每天价格

    private int likePrivilegeDayPrice1;
    //("查看喜欢我特权：按3月购买价格")
    private int likePrivilegePrice2;
    // 按三月购买每天价格

    private int likePrivilegeDayPrice2;
    //("查看喜欢我特权：按12月购买价格")
    private int likePrivilegePrice3;
    // 按12月购买每天价格
    private int likePrivilegeDayPrice3;

    // 闪聊特权
    //("闪聊特权：按1月购买价格")
    private int chatByMonthPrice1;
    // 按1月购买每天价格
    private int chatByMonthPrice1ForDay;
    //("闪聊特权：按3月购买价格")
    private int chatByMonthPrice2;
    // 按3月购买每天价格

    private int chatByMonthPrice2ForDay;
    //("闪聊特权：按12月购买价格")
    private int chatByMonthPrice3;
    // 按12月购买每天价格
    private int chatByMonthPrice3ForDay;

    /**
     * 钱包
     */
    // 在线闪聊
    //("闪聊：按1次购买价格")
    private int chatByFrequency1;
    //("闪聊：按2次购买价格")
    private int chatByFrequency2;
    //("闪聊：按10次购买价格")
    private int chatByFrequency10;
    //("闪聊：按10次以上购买,单次价格")
    private int chatByFrequency10More;

    // 超级喜欢
    //("超级喜欢：按1次购买价格")
    private int superLikeByFrequency1;
    //("超级喜欢：按2次购买价格")
    private int superLikeByFrequency2;
    //("超级喜欢：按10次购买价格")
    private int superLikeByFrequency10;
    //("超级喜欢：按10次以上购买,单次价格")
    private int superLikeByFrequency10More;

    // 闪聊偷看
    //("闪聊偷看：按1次购买价格")
    private int chatPeekByFrequency1;
    //("闪聊偷看：按2次购买价格")
    private int chatPeekByFrequency2;
    //("闪聊偷看：按10次购买价格")
    private int chatPeekByFrequency10;
    //("闪聊偷看：按10次以上购买,单次价格")
    private int chatPeekByFrequency10More;

    // 超级曝光
    //("超级暴光：一次只能买一次")
    private int outPrice;
    //("超级暴光：有效时间")
    private int outMinute;



    public String getV0() {
        return v0;
    }

    public void setV0(String v0) {
        this.v0 = v0;
    }

    public int getV0Price() {
        return v0Price;
    }

    public void setV0Price(int v0Price) {
        this.v0Price = v0Price;
    }

    public int getV0Quantity() {
        return v0Quantity;
    }

    public void setV0Quantity(int v0Quantity) {
        this.v0Quantity = v0Quantity;
    }

    public int getV0DayPrice() {
        return v0DayPrice;
    }

    public void setV0DayPrice(int v0DayPrice) {
        this.v0DayPrice = v0DayPrice;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public int getV1Price() {
        return v1Price;
    }

    public void setV1Price(int v1Price) {
        this.v1Price = v1Price;
    }

    public int getV1Quantity() {
        return v1Quantity;
    }

    public void setV1Quantity(int v1Quantity) {
        this.v1Quantity = v1Quantity;
    }

    public int getV1DayPrice() {
        return v1DayPrice;
    }

    public void setV1DayPrice(int v1DayPrice) {
        this.v1DayPrice = v1DayPrice;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public int getV2Price() {
        return v2Price;
    }

    public void setV2Price(int v2Price) {
        this.v2Price = v2Price;
    }

    public int getV2Quantity() {
        return v2Quantity;
    }

    public void setV2Quantity(int v2Quantity) {
        this.v2Quantity = v2Quantity;
    }

    public int getV2DayPrice() {
        return v2DayPrice;
    }

    public void setV2DayPrice(int v2DayPrice) {
        this.v2DayPrice = v2DayPrice;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public int getV3Price() {
        return v3Price;
    }

    public void setV3Price(int v3Price) {
        this.v3Price = v3Price;
    }

    public int getV3Quantity() {
        return v3Quantity;
    }

    public void setV3Quantity(int v3Quantity) {
        this.v3Quantity = v3Quantity;
    }

    public int getV3DayPrice() {
        return v3DayPrice;
    }

    public void setV3DayPrice(int v3DayPrice) {
        this.v3DayPrice = v3DayPrice;
    }

    public int getLikePrivilegePrice1() {
        return likePrivilegePrice1;
    }

    public void setLikePrivilegePrice1(int likePrivilegePrice1) {
        this.likePrivilegePrice1 = likePrivilegePrice1;
    }

    public int getLikePrivilegeDayPrice1() {
        return likePrivilegeDayPrice1;
    }

    public void setLikePrivilegeDayPrice1(int likePrivilegeDayPrice1) {
        this.likePrivilegeDayPrice1 = likePrivilegeDayPrice1;
    }

    public int getLikePrivilegePrice2() {
        return likePrivilegePrice2;
    }

    public void setLikePrivilegePrice2(int likePrivilegePrice2) {
        this.likePrivilegePrice2 = likePrivilegePrice2;
    }

    public int getLikePrivilegeDayPrice2() {
        return likePrivilegeDayPrice2;
    }

    public void setLikePrivilegeDayPrice2(int likePrivilegeDayPrice2) {
        this.likePrivilegeDayPrice2 = likePrivilegeDayPrice2;
    }

    public int getLikePrivilegePrice3() {
        return likePrivilegePrice3;
    }

    public void setLikePrivilegePrice3(int likePrivilegePrice3) {
        this.likePrivilegePrice3 = likePrivilegePrice3;
    }

    public int getLikePrivilegeDayPrice3() {
        return likePrivilegeDayPrice3;
    }

    public void setLikePrivilegeDayPrice3(int likePrivilegeDayPrice3) {
        this.likePrivilegeDayPrice3 = likePrivilegeDayPrice3;
    }

    public int getChatByMonthPrice1() {
        return chatByMonthPrice1;
    }

    public void setChatByMonthPrice1(int chatByMonthPrice1) {
        this.chatByMonthPrice1 = chatByMonthPrice1;
    }

    public int getChatByMonthPrice1ForDay() {
        return chatByMonthPrice1ForDay;
    }

    public void setChatByMonthPrice1ForDay(int chatByMonthPrice1ForDay) {
        this.chatByMonthPrice1ForDay = chatByMonthPrice1ForDay;
    }

    public int getChatByMonthPrice2() {
        return chatByMonthPrice2;
    }

    public void setChatByMonthPrice2(int chatByMonthPrice2) {
        this.chatByMonthPrice2 = chatByMonthPrice2;
    }

    public int getChatByMonthPrice2ForDay() {
        return chatByMonthPrice2ForDay;
    }

    public void setChatByMonthPrice2ForDay(int chatByMonthPrice2ForDay) {
        this.chatByMonthPrice2ForDay = chatByMonthPrice2ForDay;
    }

    public int getChatByMonthPrice3() {
        return chatByMonthPrice3;
    }

    public void setChatByMonthPrice3(int chatByMonthPrice3) {
        this.chatByMonthPrice3 = chatByMonthPrice3;
    }

    public int getChatByMonthPrice3ForDay() {
        return chatByMonthPrice3ForDay;
    }

    public void setChatByMonthPrice3ForDay(int chatByMonthPrice3ForDay) {
        this.chatByMonthPrice3ForDay = chatByMonthPrice3ForDay;
    }

    public int getChatByFrequency1() {
        return chatByFrequency1;
    }

    public void setChatByFrequency1(int chatByFrequency1) {
        this.chatByFrequency1 = chatByFrequency1;
    }

    public int getChatByFrequency2() {
        return chatByFrequency2;
    }

    public void setChatByFrequency2(int chatByFrequency2) {
        this.chatByFrequency2 = chatByFrequency2;
    }

    public int getChatByFrequency10() {
        return chatByFrequency10;
    }

    public void setChatByFrequency10(int chatByFrequency10) {
        this.chatByFrequency10 = chatByFrequency10;
    }

    public int getChatByFrequency10More() {
        return chatByFrequency10More;
    }

    public void setChatByFrequency10More(int chatByFrequency10More) {
        this.chatByFrequency10More = chatByFrequency10More;
    }

    public int getSuperLikeByFrequency1() {
        return superLikeByFrequency1;
    }

    public void setSuperLikeByFrequency1(int superLikeByFrequency1) {
        this.superLikeByFrequency1 = superLikeByFrequency1;
    }

    public int getSuperLikeByFrequency2() {
        return superLikeByFrequency2;
    }

    public void setSuperLikeByFrequency2(int superLikeByFrequency2) {
        this.superLikeByFrequency2 = superLikeByFrequency2;
    }

    public int getSuperLikeByFrequency10() {
        return superLikeByFrequency10;
    }

    public void setSuperLikeByFrequency10(int superLikeByFrequency10) {
        this.superLikeByFrequency10 = superLikeByFrequency10;
    }

    public int getSuperLikeByFrequency10More() {
        return superLikeByFrequency10More;
    }

    public void setSuperLikeByFrequency10More(int superLikeByFrequency10More) {
        this.superLikeByFrequency10More = superLikeByFrequency10More;
    }

    public int getChatPeekByFrequency1() {
        return chatPeekByFrequency1;
    }

    public void setChatPeekByFrequency1(int chatPeekByFrequency1) {
        this.chatPeekByFrequency1 = chatPeekByFrequency1;
    }

    public int getChatPeekByFrequency2() {
        return chatPeekByFrequency2;
    }

    public void setChatPeekByFrequency2(int chatPeekByFrequency2) {
        this.chatPeekByFrequency2 = chatPeekByFrequency2;
    }

    public int getChatPeekByFrequency10() {
        return chatPeekByFrequency10;
    }

    public void setChatPeekByFrequency10(int chatPeekByFrequency10) {
        this.chatPeekByFrequency10 = chatPeekByFrequency10;
    }

    public int getChatPeekByFrequency10More() {
        return chatPeekByFrequency10More;
    }

    public void setChatPeekByFrequency10More(int chatPeekByFrequency10More) {
        this.chatPeekByFrequency10More = chatPeekByFrequency10More;
    }

    public int getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(int outPrice) {
        this.outPrice = outPrice;
    }

    public int getOutMinute() {
        return outMinute;
    }

    public void setOutMinute(int outMinute) {
        this.outMinute = outMinute;
    }


}
