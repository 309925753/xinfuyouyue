package com.xfyyim.cn.helper;

import com.xfyyim.cn.util.Base64;
import com.xfyyim.cn.util.secure.MAC;
import com.xfyyim.cn.util.secure.Parameter;

import java.util.Map;

public class MessageSecureHelper {
    public static void mac(String messageKey, Map<String, Object> message) {
        String mac = MAC.encodeBase64((Parameter.joinObjectValues(message)).getBytes(), Base64.decode(messageKey));
        message.put("mac", mac);
    }
}
