package com.xfyyim.cn.adapter;

public class CommentEntity  {


    /**
     * body : 有毒
     * commentId : 5eb64be023ed8512c7472bd2
     * msgId : 5eb64bbe23ed8512c7472b2f
     * nickname : 好好看看
     * time : 1589005280
     * toUserId : 10000005
     * userId : 10000005
     */

    private String body;
    private String commentId;
    private String msgId;
    private String nickname;
    private int time;
    private int toUserId;
    private int userId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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
