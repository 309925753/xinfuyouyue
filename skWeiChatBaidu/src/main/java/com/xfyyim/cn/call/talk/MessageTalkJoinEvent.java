package com.xfyyim.cn.call.talk;

import com.xfyyim.cn.bean.message.ChatMessage;

public class MessageTalkJoinEvent {
    public ChatMessage chatMessage;

    public MessageTalkJoinEvent(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
