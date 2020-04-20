package com.xfyyim.cn.bean.circle;

import java.io.Serializable;
import java.util.List;

public class TopicEntity  {


    /**
     * currentTime : 1586849054831
     * data : [{"iconUrl":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=802117390,1475066753&fm=26&gp=0.jpg","id":"5e95412b0f98283dfc88518a","time":1584945977,"title":"test","userId":10004}]
     * resultCode : 1
     */

    private long currentTime;
    private int resultCode;
    private List<DataBean> data;

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * iconUrl : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=802117390,1475066753&fm=26&gp=0.jpg
         * id : 5e95412b0f98283dfc88518a
         * time : 1584945977
         * title : test
         * userId : 10004
         */

        private String iconUrl;
        private String id;
        private int time;
        private String title;
        private String des;
        private int userId;


        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
