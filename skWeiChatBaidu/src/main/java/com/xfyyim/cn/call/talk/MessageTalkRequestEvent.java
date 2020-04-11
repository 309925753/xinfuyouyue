package com.xfyyim.cn.call.talk;

import com.xfyyim.cn.bean.message.ChatMessage;

public class MessageTalkRequestEvent {
    public ChatMessage chatMessage;

    public MessageTalkRequestEvent(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
