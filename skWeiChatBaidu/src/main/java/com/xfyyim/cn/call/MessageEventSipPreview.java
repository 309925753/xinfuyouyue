package com.xfyyim.cn.call;

import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.FriendEntity;
import com.xfyyim.cn.bean.message.ChatMessage;
import com.xfyyim.cn.db.dao.FriendDao;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public class MessageEventSipPreview {
    public final String userid;
    public final Friend friend;
    public ChatMessage message;

    public MessageEventSipPreview(String userid, Friend friend, ChatMessage message) {
        this.userid = userid;
        this.friend = friend;
        this.message = message;
    }
}