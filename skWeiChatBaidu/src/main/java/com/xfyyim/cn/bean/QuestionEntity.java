package com.xfyyim.cn.bean;

import java.util.List;

public class QuestionEntity {

        /**
         * id : 5e9d5c865b6a652407f72e0a
         * question : 如果有钱有时间，最想去做什么？
         * time : 1587371142590
         * userId : 10000045
         * userQuestionAnswer : {"answer":"睡觉","id":"5e9d5c865b6a652407f72e0b","time":1587371142595,"userId":10000045,"userQuestionId":"5e9d5c865b6a652407f72e0a"}
         */

        private String id;
        private String question;
        private long time;
        private int userId;
        private UserQuestionAnswerBean userQuestionAnswer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
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

        public UserQuestionAnswerBean getUserQuestionAnswer() {
            return userQuestionAnswer;
        }

        public void setUserQuestionAnswer(UserQuestionAnswerBean userQuestionAnswer) {
            this.userQuestionAnswer = userQuestionAnswer;
        }

        public static class UserQuestionAnswerBean {
            /**
             * answer : 睡觉
             * id : 5e9d5c865b6a652407f72e0b
             * time : 1587371142595
             * userId : 10000045
             * userQuestionId : 5e9d5c865b6a652407f72e0a
             */

            private String answer;
            private String id;
            private long time;
            private int userId;
            private String userQuestionId;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserQuestionId() {
                return userQuestionId;
            }

            public void setUserQuestionId(String userQuestionId) {
                this.userQuestionId = userQuestionId;
            }
        }


}
