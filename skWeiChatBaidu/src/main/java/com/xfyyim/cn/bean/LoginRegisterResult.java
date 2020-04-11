package com.xfyyim.cn.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class LoginRegisterResult extends LoginAuto {

    private String accessToken;
    private String httpKey;
    private String loginToken;
    private String loginKey;
    private String authKey;
    private String userId;
    @JSONField(name = "nickname")
    private String nickName;// 昵称
    private String telephone;
    private String password;
    private String areaCode;
    private int friendCount;
    // 是否进行过好友操作
    private int isupdate;
    // 1=游客（用于后台浏览数据）；2=公众号 ；3=机器账号，由系统自动生成；4=客服账号;5=管理员；6=超级管理员；7=财务；
    private Login login;
    private String account; // sk号，
    private int setAccountCount; // sk号修改次数，

    private int isSupportSecureChat;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHttpKey() {
        return httpKey;
    }

    public void setHttpKey(String httpKey) {
        this.httpKey = httpKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public int getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(int isupdate) {
        this.isupdate = isupdate;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getSetAccountCount() {
        return setAccountCount;
    }

    public void setSetAccountCount(int setAccountCount) {
        this.setAccountCount = setAccountCount;
    }

    public int getIsSupportSecureChat() {
        return isSupportSecureChat;
    }

    public void setIsSupportSecureChat(int isSupportSecureChat) {
        this.isSupportSecureChat = isSupportSecureChat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public static class Login {
        private int isFirstLogin;
        private long loginTime;
        private long offlineTime;
        private String model;
        private String osVersion;
        private String serial;
        private String latitude;
        private String longitude;

        public int getIsFirstLogin() {
            return isFirstLogin;
        }

        public void setIsFirstLogin(int isFirstLogin) {
            this.isFirstLogin = isFirstLogin;
        }

        public long getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(long loginTime) {
            this.loginTime = loginTime;
        }

        public long getOfflineTime() {
            return offlineTime;
        }

        public void setOfflineTime(long offlineTime) {
            this.offlineTime = offlineTime;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
