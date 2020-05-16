package com.xfyyim.cn.bean;

import java.io.Serializable;
import java.util.List;

public class MsgBean implements Serializable {

    /**
     * age : 0
     * body : {"images":[{"length":0,"oUrl":"http://file57.quyangapp.com/u/24/10000024/202005/o/544b94b865bd4cc99fd1991cfcac1ad0.jpg","size":0,"tUrl":"http://file57.quyangapp.com/u/24/10000024/202005/t/544b94b865bd4cc99fd1991cfcac1ad0.jpg"},{"length":0,"oUrl":"http://file57.quyangapp.com/u/24/10000024/202005/o/497ecedcbf734a46b26a84c7873ac5bf.jpg","size":0,"tUrl":"http://file57.quyangapp.com/u/24/10000024/202005/t/497ecedcbf734a46b26a84c7873ac5bf.jpg"}],"text":"","time":0,"type":2}
     * cityId : 310100
     * count : {"collect":0,"comment":1,"forward":0,"money":0,"play":0,"praise":1,"share":0,"total":2}
     * faceIdentity : 0
     * flag : 3
     * isAllowComment : 1
     * isCollect : 0
     * isPraise : 0
     * latitude : 31.093847
     * loc : {"lat":31.093847,"lng":121.521043}
     * longitude : 121.521043
     * model : MI MAX 2
     * msgId : 5eba42f8a749861d647a2466
     * nickname : 舒克开飞机
     * parentTopicId : [{"parentTopicId":"5eb90b2a9eef536980216c3b"},{"parentTopicId":"5eb90b1b9eef536980216c3a"}]
     * parentTopicIds : [{"$ref":"$.data[0].msg.parentTopicId[0]"},{"$ref":"$.data[0].msg.parentTopicId[1]"}]
     * sex : 0
     * state : 0
     * time : 1589265144
     * topicStr : #哇呜啊啊啊,#请问你是DJ吗，你猜呢？
     * topicType : 1
     * userId : 10000024
     * visible : 1
     */

    private int age;
    private BodyBean body;
    private int cityId;
    private CountBean count;
    private int faceIdentity;
    private int flag;
    private int isAllowComment;
    private int isCollect;
    private int isPraise;
    private double latitude;
    private LocBean loc;
    private double longitude;
    private String model;
    private String msgId;
    private String nickname;
    private int sex;
    private int state;
    private int time;
    private String topicStr;
    private int topicType;
    private int userId;
    private int visible;
    private List<ParentTopicIdBean> parentTopicId;
    private List<ParentTopicIdsBean> parentTopicIds;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public int getFaceIdentity() {
        return faceIdentity;
    }

    public void setFaceIdentity(int faceIdentity) {
        this.faceIdentity = faceIdentity;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getIsAllowComment() {
        return isAllowComment;
    }

    public void setIsAllowComment(int isAllowComment) {
        this.isAllowComment = isAllowComment;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LocBean getLoc() {
        return loc;
    }

    public void setLoc(LocBean loc) {
        this.loc = loc;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTopicStr() {
        return topicStr;
    }

    public void setTopicStr(String topicStr) {
        this.topicStr = topicStr;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<ParentTopicIdBean> getParentTopicId() {
        return parentTopicId;
    }

    public void setParentTopicId(List<ParentTopicIdBean> parentTopicId) {
        this.parentTopicId = parentTopicId;
    }

    public List<ParentTopicIdsBean> getParentTopicIds() {
        return parentTopicIds;
    }

    public void setParentTopicIds(List<ParentTopicIdsBean> parentTopicIds) {
        this.parentTopicIds = parentTopicIds;
    }

    public static class BodyBean implements Serializable{
        /**
         * images : [{"length":0,"oUrl":"http://file57.quyangapp.com/u/24/10000024/202005/o/544b94b865bd4cc99fd1991cfcac1ad0.jpg","size":0,"tUrl":"http://file57.quyangapp.com/u/24/10000024/202005/t/544b94b865bd4cc99fd1991cfcac1ad0.jpg"},{"length":0,"oUrl":"http://file57.quyangapp.com/u/24/10000024/202005/o/497ecedcbf734a46b26a84c7873ac5bf.jpg","size":0,"tUrl":"http://file57.quyangapp.com/u/24/10000024/202005/t/497ecedcbf734a46b26a84c7873ac5bf.jpg"}]
         * text :
         * time : 0
         * type : 2
         */

        private String text;
        private int time;
        private int type;
        private List<ImagesBean> images;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean implements Serializable{
            /**
             * length : 0
             * oUrl : http://file57.quyangapp.com/u/24/10000024/202005/o/544b94b865bd4cc99fd1991cfcac1ad0.jpg
             * size : 0
             * tUrl : http://file57.quyangapp.com/u/24/10000024/202005/t/544b94b865bd4cc99fd1991cfcac1ad0.jpg
             */

            private int length;
            private String oUrl;
            private int size;
            private String tUrl;

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getOUrl() {
                return oUrl;
            }

            public void setOUrl(String oUrl) {
                this.oUrl = oUrl;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getTUrl() {
                return tUrl;
            }

            public void setTUrl(String tUrl) {
                this.tUrl = tUrl;
            }
        }
    }

    public static class CountBean implements Serializable {
        /**
         * collect : 0
         * comment : 1
         * forward : 0
         * money : 0
         * play : 0
         * praise : 1
         * share : 0
         * total : 2
         */

        private int collect;
        private int comment;
        private int forward;
        private int money;
        private int play;
        private int praise;
        private int share;
        private int total;

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getForward() {
            return forward;
        }

        public void setForward(int forward) {
            this.forward = forward;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getPlay() {
            return play;
        }

        public void setPlay(int play) {
            this.play = play;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class LocBean implements Serializable {
        /**
         * lat : 31.093847
         * lng : 121.521043
         */

        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class ParentTopicIdBean implements Serializable{
        /**
         * parentTopicId : 5eb90b2a9eef536980216c3b
         */

        private String parentTopicId;

        public String getParentTopicId() {
            return parentTopicId;
        }

        public void setParentTopicId(String parentTopicId) {
            this.parentTopicId = parentTopicId;
        }
    }

    public static class ParentTopicIdsBean implements Serializable{
        /**
         * $ref : $.data[0].msg.parentTopicId[0]
         */

        private String $ref;

        public String get$ref() {
            return $ref;
        }

        public void set$ref(String $ref) {
            this.$ref = $ref;
        }
    }
}
