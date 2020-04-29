package com.xfyyim.cn.bean;

/**
 * VIP特权实体类
 */
public class UserVIPPrivilege {
   //("用户Id")
    private int userId;


    //("会员等级：v0 v1 v2 v3(同时只有一个)")
    private String vipLevel;
   //("普通会员按月：每天所送次数(点超级喜欢)")
    private int quantity;
    // 普通会员每月价格
    private int vipPrice;
    private String startTime;
    private String endTime;

   //("查看喜欢我特权按月：0无权 1有权")
    private int likePrivilegeFlag;
    // 查看喜欢我每月价格
    private int likePrice;
    private String likeStartTime;
    private String likeEndTime;
   //("闪聊特权：按月购买次数")
    private int chatByMonthQuantity;
    // 闪聊每月价格
    private int chatByMonthPrice;
    private String chatByMonthStartTime;
    private String chatByMonthEndTime;


    //("闪聊特权：闪聊次数")
    private int chatQuantity;
    //("闪聊特权：偷看次数")
    private int chatLookQuantity;
    //("超级暴光：0无权 1有权")
    private int outFlag;
    //("超级暴光：有效时间")
    private int outMinute;

    //("闪聊特权：是否开通，0未开通,1开通")")
    private int isChatByMonthQuantity;
    public int getIsChatByMonthQuantity() {
        return isChatByMonthQuantity;
    }

    public void setIsChatByMonthQuantity(int isChatByMonthQuantity) {
        this.isChatByMonthQuantity = isChatByMonthQuantity;
    }
    public int getSuperLikeQuantity() {
        return superLikeQuantity;
    }

    public void setSuperLikeQuantity(int superLikeQuantity) {
        this.superLikeQuantity = superLikeQuantity;
    }

    //"超级喜欢：购买次数"
    private int superLikeQuantity;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getLikePrivilegeFlag() {
        return likePrivilegeFlag;
    }

    public void setLikePrivilegeFlag(int likePrivilegeFlag) {
        this.likePrivilegeFlag = likePrivilegeFlag;
    }

    public int getLikePrice() {
        return likePrice;
    }

    public void setLikePrice(int likePrice) {
        this.likePrice = likePrice;
    }

    public String getLikeStartTime() {
        return likeStartTime;
    }

    public void setLikeStartTime(String likeStartTime) {
        this.likeStartTime = likeStartTime;
    }

    public String getLikeEndTime() {
        return likeEndTime;
    }

    public void setLikeEndTime(String likeEndTime) {
        this.likeEndTime = likeEndTime;
    }

    public int getChatByMonthQuantity() {
        return chatByMonthQuantity;
    }

    public void setChatByMonthQuantity(int chatByMonthQuantity) {
        this.chatByMonthQuantity = chatByMonthQuantity;
    }

    public int getChatByMonthPrice() {
        return chatByMonthPrice;
    }

    public void setChatByMonthPrice(int chatByMonthPrice) {
        this.chatByMonthPrice = chatByMonthPrice;
    }

    public String getChatByMonthStartTime() {
        return chatByMonthStartTime;
    }

    public void setChatByMonthStartTime(String chatByMonthStartTime) {
        this.chatByMonthStartTime = chatByMonthStartTime;
    }

    public String getChatByMonthEndTime() {
        return chatByMonthEndTime;
    }

    public void setChatByMonthEndTime(String chatByMonthEndTime) {
        this.chatByMonthEndTime = chatByMonthEndTime;
    }

    public int getChatQuantity() {
        return chatQuantity;
    }

    public void setChatQuantity(int chatQuantity) {
        this.chatQuantity = chatQuantity;
    }

    public int getChatLookQuantity() {
        return chatLookQuantity;
    }

    public void setChatLookQuantity(int chatLookQuantity) {
        this.chatLookQuantity = chatLookQuantity;
    }

    public int getOutFlag() {
        return outFlag;
    }

    public void setOutFlag(int outFlag) {
        this.outFlag = outFlag;
    }

    public int getOutMinute() {
        return outMinute;
    }

    public void setOutMinute(int outMinute) {
        this.outMinute = outMinute;
    }
    @Override
    public String toString() {
        return "UserVIPPrivilege{" +
                "userId=" + userId +
                ", vipLevel='" + vipLevel + '\'' +
                ", quantity=" + quantity +
                ", vipPrice=" + vipPrice +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", likePrivilegeFlag=" + likePrivilegeFlag +
                ", likePrice=" + likePrice +
                ", likeStartTime='" + likeStartTime + '\'' +
                ", likeEndTime='" + likeEndTime + '\'' +
                ", chatByMonthQuantity=" + chatByMonthQuantity +
                ", chatByMonthPrice=" + chatByMonthPrice +
                ", chatByMonthStartTime='" + chatByMonthStartTime + '\'' +
                ", chatByMonthEndTime='" + chatByMonthEndTime + '\'' +
                ", chatQuantity=" + chatQuantity +
                ", chatLookQuantity=" + chatLookQuantity +
                ", outFlag=" + outFlag +
                ", outMinute=" + outMinute +
                '}';
    }

}
