package com.xfyyim.cn.call.talk;

import com.xfyyim.cn.bean.message.ChatMessage;

public class MessageTalkOnlineEvent {
    public ChatMessage chatMessage;

    public MessageTalkOnlineEvent(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
