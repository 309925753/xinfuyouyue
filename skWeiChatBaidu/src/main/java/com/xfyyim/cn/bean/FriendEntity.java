package com.xfyyim.cn.bean;

import java.io.Serializable;
import java.util.List;

public class FriendEntity implements Serializable {

    /**
     * currentTime : 1585651751078
     * data : [{"blacklist":0,"chatRecordTimeOut":-1,"createTime":1585555678,"encryptType":0,"fromAddType":4,"isBeenBlack":0,"isOpenSnapchat":0,"lastTalkTime":0,"modifyTime":0,"msgNum":0,"offlineNoPushMsg":0,"openTopChatTime":0,"status":2,"toFriendsRole":[2],"toNickname":"客服公众号","toUserId":10000,"toUserType":2,"userId":10000016},{"blacklist":0,"chatRecordTimeOut":-1,"createTime":1585563011,"encryptType":0,"fromAddType":0,"isBeenBlack":0,"isOpenSnapchat":0,"lastTalkTime":0,"modifyTime":1585563912,"msgNum":0,"offlineNoPushMsg":0,"openTopChatTime":0,"status":2,"toFriendsRole":[],"toNickname":"","toUserId":10000017,"toUserType":0,"userId":10000016}]
     * resultCode : 1
     */

    private long currentTime;
    private int resultCode;
    private List<DataBean> data;

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * blacklist : 0
         * chatRecordTimeOut : -1.0
         * createTime : 1585555678
         * encryptType : 0
         * fromAddType : 4
         * isBeenBlack : 0
         * isOpenSnapchat : 0
         * lastTalkTime : 0
         * modifyTime : 0
         * msgNum : 0
         * offlineNoPushMsg : 0
         * openTopChatTime : 0
         * status : 2
         * toFriendsRole : [2]
         * toNickname : 客服公众号
         * toUserId : 10000
         * toUserType : 2
         * userId : 10000016
         * lastChatContent  最后一条消息
         */

        private int blacklist;
        private  String lastChatContent;
        private double chatRecordTimeOut;
        private int createTime;
        private int encryptType;
        private int fromAddType;
        private int isBeenBlack;
        private int isOpenSnapchat;
        private int lastTalkTime;
        private int modifyTime;
        private int msgNum;
        private int offlineNoPushMsg;
        private int openTopChatTime;
        private int status;
        private String toNickname;
        private int toUserId;
        private int toUserType;
        private int userId;
        private List<Integer> toFriendsRole;


        public String getLastChatContent() {
            return lastChatContent;
        }

        public void setLastChatContent(String lastChatContent) {
            this.lastChatContent = lastChatContent;
        }

        public int getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(int blacklist) {
            this.blacklist = blacklist;
        }

        public double getChatRecordTimeOut() {
            return chatRecordTimeOut;
        }

        public void setChatRecordTimeOut(double chatRecordTimeOut) {
            this.chatRecordTimeOut = chatRecordTimeOut;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getEncryptType() {
            return encryptType;
        }

        public void setEncryptType(int encryptType) {
            this.encryptType = encryptType;
        }

        public int getFromAddType() {
            return fromAddType;
        }

        public void setFromAddType(int fromAddType) {
            this.fromAddType = fromAddType;
        }

        public int getIsBeenBlack() {
            return isBeenBlack;
        }

        public void setIsBeenBlack(int isBeenBlack) {
            this.isBeenBlack = isBeenBlack;
        }

        public int getIsOpenSnapchat() {
            return isOpenSnapchat;
        }

        public void setIsOpenSnapchat(int isOpenSnapchat) {
            this.isOpenSnapchat = isOpenSnapchat;
        }

        public int getLastTalkTime() {
            return lastTalkTime;
        }

        public void setLastTalkTime(int lastTalkTime) {
            this.lastTalkTime = lastTalkTime;
        }

        public int getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(int modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getMsgNum() {
            return msgNum;
        }

        public void setMsgNum(int msgNum) {
            this.msgNum = msgNum;
        }

        public int getOfflineNoPushMsg() {
            return offlineNoPushMsg;
        }

        public void setOfflineNoPushMsg(int offlineNoPushMsg) {
            this.offlineNoPushMsg = offlineNoPushMsg;
        }

        public int getOpenTopChatTime() {
            return openTopChatTime;
        }

        public void setOpenTopChatTime(int openTopChatTime) {
            this.openTopChatTime = openTopChatTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToNickname() {
            return toNickname;
        }

        public void setToNickname(String toNickname) {
            this.toNickname = toNickname;
        }

        public int getToUserId() {
            return toUserId;
        }

        public void setToUserId(int toUserId) {
            this.toUserId = toUserId;
        }

        public int getToUserType() {
            return toUserType;
        }

        public void setToUserType(int toUserType) {
            this.toUserType = toUserType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<Integer> getToFriendsRole() {
            return toFriendsRole;
        }

        public void setToFriendsRole(List<Integer> toFriendsRole) {
            this.toFriendsRole = toFriendsRole;
        }
    }
}
