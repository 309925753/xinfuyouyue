package com.xfyyim.cn.bean;

public class LikeMeBean {
    /**
     * id : 5e983f4cc4659816f7f6086a
     * isSuperLike : 0
     * isUnLike : 0
     * likeTime : 1587035980189
     * likeUserId : 10000028
     * nickname : 1
     * userId : 10000027
     */

    private String id;
    private int isSuperLike;
    private int isUnLike;
    private int likeUserAge;
    private long likeTime;

    private int likeUserId;
    private String nickname;
    private int userId;

    public int getLikeUserAge() {
        return likeUserAge;
    }

    public void setLikeUserAge(int likeUserAge) {
        this.likeUserAge = likeUserAge;
    }



    public String getMyNickname() {
        return myNickname;
    }

    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }

    private String myNickname;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsSuperLike() {
        return isSuperLike;
    }

    public void setIsSuperLike(int isSuperLike) {
        this.isSuperLike = isSuperLike;
    }

    public int getIsUnLike() {
        return isUnLike;
    }

    public void setIsUnLike(int isUnLike) {
        this.isUnLike = isUnLike;
    }

    public long getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(long likeTime) {
        this.likeTime = likeTime;
    }

    public int getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(int likeUserId) {
        this.likeUserId = likeUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
