package com.xfyyim.cn.bean.message;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatMessageTest {

    @Test
    public void testJsonBoolean() throws Exception {
        String json = "{\"a\":false}";
        assertEquals(0, JSON.parseObject(json)
                .getIntValue("a"));
    }

}