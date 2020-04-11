package com.xfyyim.cn.bean.event;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public class MessageUploadChatRecord {
    public String chatIds;

    /**
     * @see com.xfyyim.cn.ui.message.ChatActivity
     */
    public MessageUploadChatRecord(String chatIds) {
        this.chatIds = chatIds;
    }
}