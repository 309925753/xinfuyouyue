package com.xfyyim.cn.bean;

public class InviteEntity {


    /**
     * id : 5eaa785e8c4dbb9a180fb041
     * inviteCode : 1212121
     * time : 1588211493
     * toNickName : test4
     * toUserId : 10000004
     * userId : 10000012
     */

    private String id;
    private String inviteCode;
    private int time;
    private String toNickName;
    private int toUserId;
    private int userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
