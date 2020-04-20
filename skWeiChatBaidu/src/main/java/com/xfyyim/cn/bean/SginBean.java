package com.xfyyim.cn.bean;

public class SginBean {


    /**
     * resultCode : 1
     * currentTime : 1587103704562
     * data : {"workConfig":"高管,创始人,职业经理人,咨询顾问,市场,产品,客服","id":"5e97c1da47a60b44b1ca620b","questionsConfig":"可以接受的约会有哪些？,如果有钱有时间，最想去做什么？,有没有一首歌，喜欢了很多年？","movieConfig":"肖申克救赎,阿甘正传,黑客帝国,霸王别姬,教父","tagConfig":"敢爱敢恨,文艺,喜欢简单,萌萌哒,女汉子,强迫症,拖延症","tastesConfig":"跑步,羽毛球,健身房,射击，爬山,骑马","industryConfig":"学生,文化/艺术,影视/娱乐,金融,医药/健康,工业/制造业,IT/互联网/通信","sportsConfig":"成都,三亚,丽江,香格里拉","bookAndComicConfig":"金庸,古龙,鲁迅,韩寒","musicConfig":"欧美,日韩,流行,摇滚,电子","foodConfig":"北京烤鸭,港式奶茶,火锅,烤串,麻辣烫"}
     */

    private int resultCode;
    private long currentTime;
    private DataBean data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

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

    public static class DataBean {
        /**
         * workConfig : 高管,创始人,职业经理人,咨询顾问,市场,产品,客服
         * id : 5e97c1da47a60b44b1ca620b
         * questionsConfig : 可以接受的约会有哪些？,如果有钱有时间，最想去做什么？,有没有一首歌，喜欢了很多年？
         * movieConfig : 肖申克救赎,阿甘正传,黑客帝国,霸王别姬,教父
         * tagConfig : 敢爱敢恨,文艺,喜欢简单,萌萌哒,女汉子,强迫症,拖延症
         * tastesConfig : 跑步,羽毛球,健身房,射击，爬山,骑马
         * industryConfig : 学生,文化/艺术,影视/娱乐,金融,医药/健康,工业/制造业,IT/互联网/通信
         * sportsConfig : 成都,三亚,丽江,香格里拉
         * bookAndComicConfig : 金庸,古龙,鲁迅,韩寒
         * musicConfig : 欧美,日韩,流行,摇滚,电子
         * foodConfig : 北京烤鸭,港式奶茶,火锅,烤串,麻辣烫
         */

        private String workConfig;
        private String id;
        private String questionsConfig;
        private String movieConfig;
        private String tagConfig;
        private String tastesConfig;
        private String industryConfig;
        private String sportsConfig;
        private String bookAndComicConfig;
        private String musicConfig;
        private String foodConfig;

        public String getWorkConfig() {
            return workConfig;
        }

        public void setWorkConfig(String workConfig) {
            this.workConfig = workConfig;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionsConfig() {
            return questionsConfig;
        }

        public void setQuestionsConfig(String questionsConfig) {
            this.questionsConfig = questionsConfig;
        }

        public String getMovieConfig() {
            return movieConfig;
        }

        public void setMovieConfig(String movieConfig) {
            this.movieConfig = movieConfig;
        }

        public String getTagConfig() {
            return tagConfig;
        }

        public void setTagConfig(String tagConfig) {
            this.tagConfig = tagConfig;
        }

        public String getTastesConfig() {
            return tastesConfig;
        }

        public void setTastesConfig(String tastesConfig) {
            this.tastesConfig = tastesConfig;
        }

        public String getIndustryConfig() {
            return industryConfig;
        }

        public void setIndustryConfig(String industryConfig) {
            this.industryConfig = industryConfig;
        }

        public String getSportsConfig() {
            return sportsConfig;
        }

        public void setSportsConfig(String sportsConfig) {
            this.sportsConfig = sportsConfig;
        }

        public String getBookAndComicConfig() {
            return bookAndComicConfig;
        }

        public void setBookAndComicConfig(String bookAndComicConfig) {
            this.bookAndComicConfig = bookAndComicConfig;
        }

        public String getMusicConfig() {
            return musicConfig;
        }

        public void setMusicConfig(String musicConfig) {
            this.musicConfig = musicConfig;
        }

        public String getFoodConfig() {
            return foodConfig;
        }

        public void setFoodConfig(String foodConfig) {
            this.foodConfig = foodConfig;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "workConfig='" + workConfig + '\'' +
                    ", id='" + id + '\'' +
                    ", questionsConfig='" + questionsConfig + '\'' +
                    ", movieConfig='" + movieConfig + '\'' +
                    ", tagConfig='" + tagConfig + '\'' +
                    ", tastesConfig='" + tastesConfig + '\'' +
                    ", industryConfig='" + industryConfig + '\'' +
                    ", sportsConfig='" + sportsConfig + '\'' +
                    ", bookAndComicConfig='" + bookAndComicConfig + '\'' +
                    ", musicConfig='" + musicConfig + '\'' +
                    ", foodConfig='" + foodConfig + '\'' +
                    '}';
        }
    }
}
