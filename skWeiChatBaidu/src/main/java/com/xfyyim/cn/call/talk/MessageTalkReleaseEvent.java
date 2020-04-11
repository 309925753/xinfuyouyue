package com.xfyyim.cn.call.talk;

import com.xfyyim.cn.bean.message.ChatMessage;

public class MessageTalkReleaseEvent {
    public ChatMessage chatMessage;

    public MessageTalkReleaseEvent(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
