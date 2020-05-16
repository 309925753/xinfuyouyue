package com.xfyyim.cn.bean.circle;

import java.io.Serializable;
import java.util.List;

public class TopicEntity  {


    /**
     * currentTime : 1587611670431
     * data : {"quantity":0,"list":[{"des":"44","iconUrl":"http://file57.quyangapp.com/u/1000/1000/202004/o/4c6eff2fde9c4334a1eb1aea58ac9b68.jpg","id":"5e9ecb057a0a3d749e54e635","time":1587464965814,"title":"44","userId":1000},{"des":"2222","iconUrl":"http://file57.quyangapp.com/u/1000/1000/202004/o/5f221198f9ad45a4bf40c64cedbbb5b2.jpg","id":"5e9ecac87a0a3d749e54e634","time":1587464904176,"title":"2222","userId":1000},{"des":"www","iconUrl":"http://file57.quyangapp.com/u/1000/1000/202004/o/e306fa3b02d645c78c7f8aad05115f66.jpg","id":"5e9ec9db7a0a3d749e54e3ea","time":1587464667049,"title":"www","userId":1000},{"des":"测试ddddddddddddddddddddddddddddddddddd","iconUrl":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=802117390,1475066753&fm=26&gp=0.jpg","id":"5e95412b0f98283dfc88518a","time":1584945977,"title":"test","userId":10004}]}
     * resultCode : 1
     */

    private long currentTime;
    private DataBean data;
    private int resultCode;

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public static class DataBean implements Serializable {
        /**
         * quantity : 0
         * list : [{"des":"44","iconUrl":"http://file57.quyangapp.com/u/1000/1000/202004/o/4c6eff2fde9c4334a1eb1aea58ac9b68.jpg","id":"5e9ecb057a0a3d749e54e635","time":1587464965814,"title":"44","userId":1000},{"des":"2222","iconUrl":"http://file57.quyangapp.com/u/1000/1000/202004/o/5f221198f9ad45a4bf40c64cedbbb5b2.jpg","id":"5e9ecac87a0a3d749e54e634","time":1587464904176,"title":"2222","userId":1000},{"des":"www","iconUrl":"http://file57.quyangapp.com/u/1000/1000/202004/o/e306fa3b02d645c78c7f8aad05115f66.jpg","id":"5e9ec9db7a0a3d749e54e3ea","time":1587464667049,"title":"www","userId":1000},{"des":"测试ddddddddddddddddddddddddddddddddddd","iconUrl":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=802117390,1475066753&fm=26&gp=0.jpg","id":"5e95412b0f98283dfc88518a","time":1584945977,"title":"test","userId":10004}]
         */

        private int quantity;
        private List<ListBean> list;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * des : 44
             * iconUrl : http://file57.quyangapp.com/u/1000/1000/202004/o/4c6eff2fde9c4334a1eb1aea58ac9b68.jpg
             * id : 5e9ecb057a0a3d749e54e635
             * time : 1587464965814
             * title : 44
             * userId : 1000
             */

            private String des;
            private String iconUrl;
            private String id;
            private long time;
            private String title;
            private int userId;

            private boolean isSelectTopic;

            public boolean isSelectTopic() {
                return isSelectTopic;
            }

            public void setSelectTopic(boolean selectTopic) {
                isSelectTopic = selectTopic;
            }

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

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getTitle() {
                if (!title.contains("#")&&title.startsWith("#")){
                    title="#"+title;
                }
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
}
