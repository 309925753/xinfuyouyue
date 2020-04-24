package com.xfyyim.cn.bean;

public class PhotoEntity {

    /**
     * photoUtl : http://file57.quyangapp.com/u/45/10000045/202004/o/bf025baeb5fa436aa66aaed51d790c1d.jpg
     * time : 1587470977210
     * userId : 10000045
     * id : 5e9ee2811a1f363e0f214438
     */

    private String photoUtl;
    private long time;
    private int userId;
    private String id;

    public String getPhotoUtl() {
        return photoUtl;
    }

    public void setPhotoUtl(String photoUtl) {
        this.photoUtl = photoUtl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
