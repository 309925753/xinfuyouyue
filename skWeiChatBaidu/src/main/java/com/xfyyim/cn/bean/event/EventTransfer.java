package com.xfyyim.cn.bean.event;

import com.xfyyim.cn.bean.message.ChatMessage;

public class EventTransfer {
    private ChatMessage chatMessage;

    public EventTransfer(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
