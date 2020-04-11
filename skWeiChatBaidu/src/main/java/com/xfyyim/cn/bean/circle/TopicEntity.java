package com.xfyyim.cn.bean.circle;

import java.util.List;

public class TopicEntity  {


    /**
     * currentTime : 1585906535994
     * data : {"topics":[{"body":{"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"text":"reply content 2","time":0,"title":"reply title 2","type":2},"cityId":0,"count":{"collect":0,"comment":35,"forward":0,"money":0,"play":0,"praise":1,"share":0,"total":36},"flag":4,"isAllowComment":0,"isCollect":0,"isPraise":0,"latitude":123.5099338737541,"longitude":34.09009042757199,"msgId":"5e78859ed784da5b5cd4b5a9","nickname":"客服公众号","parentTopicId":"5e787dc60a7a3b3c3c62d3cc","state":0,"time":1584956830,"topicType":0,"userId":10000,"visible":1},{"body":{"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"text":"reply content 2","time":0,"title":"reply title 2","type":2},"cityId":0,"count":{"collect":0,"comment":2,"forward":0,"money":0,"play":0,"praise":3,"share":0,"total":5},"flag":4,"isAllowComment":0,"isCollect":0,"isPraise":0,"latitude":0,"longitude":0,"msgId":"5e788331d963af2fdd9f7765","nickname":"客服公众号","parentTopicId":"5e787dc60a7a3b3c3c62d3cc","state":0,"time":1584956209,"topicType":0,"userId":10000,"visible":1},{"body":{"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"text":"reply content","time":0,"title":"reply title","type":2},"cityId":0,"count":{"collect":0,"comment":0,"forward":0,"money":0,"play":0,"praise":2,"share":0,"total":2},"flag":4,"isAllowComment":0,"isCollect":0,"isPraise":0,"latitude":0,"longitude":0,"msgId":"5e7882b3d963af2fdd9f7763","nickname":"客服公众号","parentTopicId":"5e787dc60a7a3b3c3c62d3cc","state":0,"time":1584956083,"topicType":0,"userId":10000,"visible":1},{"body":{"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"text":"aabbccdd","time":0,"title":"abcd","type":2},"cityId":0,"count":{"collect":0,"comment":0,"forward":0,"money":0,"play":0,"praise":0,"share":0,"total":0},"flag":4,"isAllowComment":0,"isCollect":0,"isPraise":0,"latitude":0,"longitude":0,"msgId":"5e787dc60a7a3b3c3c62d3cc","nickname":"客服公众号","state":0,"time":1584954822,"topicType":1,"userId":10000,"visible":1},{"body":{"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"time":0,"type":2},"cityId":0,"count":{"collect":0,"comment":0,"forward":0,"money":0,"play":0,"praise":0,"share":0,"total":0},"flag":4,"isAllowComment":0,"isCollect":0,"isPraise":0,"latitude":0,"longitude":0,"msgId":"5e787d330a7a3b3c3c62d3cb","nickname":"客服公众号","state":0,"time":1584954675,"topicType":1,"userId":10000,"visible":1},{"body":{"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"time":0,"type":2},"cityId":0,"count":{"collect":0,"comment":0,"forward":0,"money":0,"play":0,"praise":0,"share":0,"total":0},"flag":4,"isAllowComment":0,"isCollect":0,"isPraise":0,"latitude":0,"longitude":0,"msgId":"5e787ba40a7a3b3c3c62d3c8","nickname":"客服公众号","state":0,"time":1584954276,"topicType":0,"userId":10000,"visible":1}]}
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

    public static class DataBean {
        private List<TopicsBean> topics;

        public List<TopicsBean> getTopics() {
            return topics;
        }

        public void setTopics(List<TopicsBean> topics) {
            this.topics = topics;
        }

        public static class TopicsBean {
            /**
             * body : {"images":[{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}],"text":"reply content 2","time":0,"title":"reply title 2","type":2}
             * cityId : 0
             * count : {"collect":0,"comment":35,"forward":0,"money":0,"play":0,"praise":1,"share":0,"total":36}
             * flag : 4
             * isAllowComment : 0
             * isCollect : 0
             * isPraise : 0
             * latitude : 123.5099338737541
             * longitude : 34.09009042757199
             * msgId : 5e78859ed784da5b5cd4b5a9
             * nickname : 客服公众号
             * parentTopicId : 5e787dc60a7a3b3c3c62d3cc
             * state : 0
             * time : 1584956830
             * topicType : 0
             * userId : 10000
             * visible : 1
             */

            private BodyBean body;
            private int cityId;
            private CountBean count;
            private int flag;
            private int isAllowComment;
            private int isCollect;
            private int isPraise;
            private double latitude;
            private double longitude;
            private String msgId;
            private String nickname;
            private String parentTopicId;
            private int state;
            private int time;
            private int topicType;
            private int userId;
            private int visible;

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

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
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

            public String getParentTopicId() {
                return parentTopicId;
            }

            public void setParentTopicId(String parentTopicId) {
                this.parentTopicId = parentTopicId;
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

            public static class BodyBean {
                /**
                 * images : [{"length":0,"oUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg","size":0,"tUrl":"https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg"}]
                 * text : reply content 2
                 * time : 0
                 * title : reply title 2
                 * type : 2
                 */

                private String text;
                private int time;
                private String title;
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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
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

                public static class ImagesBean {
                    /**
                     * length : 0
                     * oUrl : https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg
                     * size : 0
                     * tUrl : https://static.fotor.com.cn/assets/projects/pages/8dc20c30-f6dd-11e8-b192-db374e5d4e61_fc2b5a80-5d80-4678-8cbd-54061c90a092_thumb.jpg
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

            public static class CountBean {
                /**
                 * collect : 0
                 * comment : 35
                 * forward : 0
                 * money : 0
                 * play : 0
                 * praise : 1
                 * share : 0
                 * total : 36
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
        }
    }
}
