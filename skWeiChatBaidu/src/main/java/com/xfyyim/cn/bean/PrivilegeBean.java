package com.xfyyim.cn.bean;

/**
 * 我的特权页面
 */
public class PrivilegeBean {
    private int likeFlag = 0; // 0 - 未喜欢（关注）过， 1 - 已经喜欢（关注）过

    private int vipLevel = 0; //  -1不是会员  0- 普通会员 1- 一级vip 2- 二级vip 3- 三级vip
    private String vipExpiredTime = null; // VIP过期时间

    private int lookWhoLikeMe;  // 普通特权-购买后可以查看谁喜欢我，有时限 0 - 无， 1 - 有
    private String lookWhoLikeMeExpiredTime = null; // 超级曝光过期时间
    // 闪聊
    private int flashMatch = 0 ; // 普通特权-闪聊配对 0 - 无， 1 - 有
    private String flashMatchExpiredTime = null;  // 闪聊配对过期

    private int matchTimesPerDay = 0; // 每日获得的闪聊配对次数

    private int matchTimesBuy = 0; // 购买的闪聊配对次数
    // 超级曝光 - 无字段
    // 超级喜欢次数
    private int superLikeTimesPerDay = 0; // 每日获得的超级喜欢次数
    private int superLikeTimesBuy = 0; // 购买的超级喜欢次数
    // 是否可以偷窥
    private int lookAtOther = 0 ; // 偷窥 0 - 无， 1 - 有

    public int getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(int likeFlag) {
        this.likeFlag = likeFlag;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipExpiredTime() {
        return vipExpiredTime;
    }

    public void setVipExpiredTime(String vipExpiredTime) {
        this.vipExpiredTime = vipExpiredTime;
    }

    public int getLookWhoLikeMe() {
        return lookWhoLikeMe;
    }

    public void setLookWhoLikeMe(int lookWhoLikeMe) {
        this.lookWhoLikeMe = lookWhoLikeMe;
    }

    public String getLookWhoLikeMeExpiredTime() {
        return lookWhoLikeMeExpiredTime;
    }

    public void setLookWhoLikeMeExpiredTime(String lookWhoLikeMeExpiredTime) {
        this.lookWhoLikeMeExpiredTime = lookWhoLikeMeExpiredTime;
    }

    public int getFlashMatch() {
        return flashMatch;
    }

    public void setFlashMatch(int flashMatch) {
        this.flashMatch = flashMatch;
    }

    public String getFlashMatchExpiredTime() {
        return flashMatchExpiredTime;
    }

    public void setFlashMatchExpiredTime(String flashMatchExpiredTime) {
        this.flashMatchExpiredTime = flashMatchExpiredTime;
    }

    public int getMatchTimesPerDay() {
        return matchTimesPerDay;
    }

    public void setMatchTimesPerDay(int matchTimesPerDay) {
        this.matchTimesPerDay = matchTimesPerDay;
    }

    public int getMatchTimesBuy() {
        return matchTimesBuy;
    }

    public void setMatchTimesBuy(int matchTimesBuy) {
        this.matchTimesBuy = matchTimesBuy;
    }

    public int getSuperLikeTimesPerDay() {
        return superLikeTimesPerDay;
    }

    public void setSuperLikeTimesPerDay(int superLikeTimesPerDay) {
        this.superLikeTimesPerDay = superLikeTimesPerDay;
    }

    public int getSuperLikeTimesBuy() {
        return superLikeTimesBuy;
    }

    public void setSuperLikeTimesBuy(int superLikeTimesBuy) {
        this.superLikeTimesBuy = superLikeTimesBuy;
    }

    public int getLookAtOther() {
        return lookAtOther;
    }

    public void setLookAtOther(int lookAtOther) {
        this.lookAtOther = lookAtOther;
    }


}
