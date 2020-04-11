package com.xfyyim.cn.bean;

public class Photo {

    private int pos;
    private  String tUrl;
    private String oUrl;


    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String gettUrl() {
        return tUrl;
    }

    public void settUrl(String tUrl) {
        this.tUrl = tUrl;
    }

    public String getoUrl() {
        return oUrl;
    }

    public void setoUrl(String oUrl) {
        this.oUrl = oUrl;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "pos=" + pos +
                ", tUrl='" + tUrl + '\'' +
                ", oUrl='" + oUrl + '\'' +
                '}';
    }
}
