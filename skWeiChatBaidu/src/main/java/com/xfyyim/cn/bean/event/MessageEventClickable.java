package com.xfyyim.cn.bean.event;

import com.xfyyim.cn.bean.message.ChatMessage;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public class MessageEventClickable {
    public final ChatMessage event;

    public MessageEventClickable(ChatMessage event) {
        this.event = event;
    }
}