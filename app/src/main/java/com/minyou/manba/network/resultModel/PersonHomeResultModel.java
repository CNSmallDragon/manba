package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */

public class PersonHomeResultModel extends BaseResultModel {

    /**
     * detail : null
     * result : {"userId":7,"nickName":"秦时明月","iconUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","backgroundUrl":null,"signName":"嘻嘻","sex":2,"fenNum":0,"followNum":0,"follow":null,"imageList":null,"zoneList":[{"id":11,"userId":7,"zoneContent":"测试发帖","zoneImage":["http://res.mymanba.cn/Fl0e5s0ddcVtZ1W3_upNr3ToHPh9","http://res.mymanba.cn/Fs471nq7Yq0t9MaM6bztixgMEh-n"],"publishTime":1514103839000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":1,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":12,"userId":7,"zoneContent":"测试发帖","zoneImage":["http://res.mymanba.cn/Fti-yL-L7eGvAa_ar9SJLHqvftpk","http://res.mymanba.cn/Fq3RwaFmQ12Y5njT6OzJu5pKSxse"],"publishTime":1514103986000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":3,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":13,"userId":7,"zoneContent":"测试发帖123","zoneImage":["http://res.mymanba.cn/Fti-yL-L7eGvAa_ar9SJLHqvftpk","http://res.mymanba.cn/Fl0e5s0ddcVtZ1W3_upNr3ToHPh9"],"publishTime":1514105199000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":4,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":14,"userId":7,"zoneContent":"测试发帖123","zoneImage":["http://res.mymanba.cn/Fti-yL-L7eGvAa_ar9SJLHqvftpk","http://res.mymanba.cn/Fiibqq7sIPKyDIomE4K_11h1Guj4"],"publishTime":1514105216000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":5,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":15,"userId":7,"zoneContent":"一张图片","zoneImage":["http://res.mymanba.cn/FjXdjCUuj2cO658zlnznZQMvr-0f"],"publishTime":1514131523000,"guildId":4,"guildName":"番剧","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":12,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":16,"userId":7,"zoneContent":"三张图","zoneImage":["http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/FpyGEpqaAkiiUs-hAMgVPFju60Ih"],"publishTime":1514132562000,"guildId":3,"guildName":"情感&日常","upvoteNum":2,"favoriteNum":0,"commentNum":0,"lookNum":42,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":true,"favorite":false},{"id":20,"userId":7,"zoneContent":"多来点","zoneImage":["http://res.mymanba.cn/FqzxBoScyQw8OUnTqbWMzZD_uIUk","http://res.mymanba.cn/Fmr7r1GbYH8vNa_mMlYxJIyE5x2W","http://res.mymanba.cn/FjGGWyKH-QbszD5B7garS7_7QNPt","http://res.mymanba.cn/FvEzf7BDw81KQqtlHjw-4sFJ6WRh","http://res.mymanba.cn/Fr4Irc5fvlHO8x61dWN6tkH4wIIc","http://res.mymanba.cn/FjpdgaQ1gRJqHeEQPY-cL1-syVqK"],"publishTime":1514795179000,"guildId":4,"guildName":"番剧","upvoteNum":3,"favoriteNum":2,"commentNum":3,"lookNum":231,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":true,"favorite":false}]}
     */

    private PersonResultBean result;

    public PersonResultBean getResult() {
        return result;
    }

    public void setResult(PersonResultBean result) {
        this.result = result;
    }

    public static class PersonResultBean {
        /**
         * userId : 7
         * nickName : 秦时明月
         * iconUrl : http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW
         * backgroundUrl : null
         * signName : 嘻嘻
         * sex : 2
         * fenNum : 0
         * followNum : 0
         * follow : null
         * imageList : null
         * zoneList : [{"id":11,"userId":7,"zoneContent":"测试发帖","zoneImage":["http://res.mymanba.cn/Fl0e5s0ddcVtZ1W3_upNr3ToHPh9","http://res.mymanba.cn/Fs471nq7Yq0t9MaM6bztixgMEh-n"],"publishTime":1514103839000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":1,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":12,"userId":7,"zoneContent":"测试发帖","zoneImage":["http://res.mymanba.cn/Fti-yL-L7eGvAa_ar9SJLHqvftpk","http://res.mymanba.cn/Fq3RwaFmQ12Y5njT6OzJu5pKSxse"],"publishTime":1514103986000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":3,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":13,"userId":7,"zoneContent":"测试发帖123","zoneImage":["http://res.mymanba.cn/Fti-yL-L7eGvAa_ar9SJLHqvftpk","http://res.mymanba.cn/Fl0e5s0ddcVtZ1W3_upNr3ToHPh9"],"publishTime":1514105199000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":4,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":14,"userId":7,"zoneContent":"测试发帖123","zoneImage":["http://res.mymanba.cn/Fti-yL-L7eGvAa_ar9SJLHqvftpk","http://res.mymanba.cn/Fiibqq7sIPKyDIomE4K_11h1Guj4"],"publishTime":1514105216000,"guildId":3,"guildName":"情感&日常","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":5,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":15,"userId":7,"zoneContent":"一张图片","zoneImage":["http://res.mymanba.cn/FjXdjCUuj2cO658zlnznZQMvr-0f"],"publishTime":1514131523000,"guildId":4,"guildName":"番剧","upvoteNum":0,"favoriteNum":0,"commentNum":0,"lookNum":12,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":false,"favorite":false},{"id":16,"userId":7,"zoneContent":"三张图","zoneImage":["http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/FpyGEpqaAkiiUs-hAMgVPFju60Ih"],"publishTime":1514132562000,"guildId":3,"guildName":"情感&日常","upvoteNum":2,"favoriteNum":0,"commentNum":0,"lookNum":42,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":true,"favorite":false},{"id":20,"userId":7,"zoneContent":"多来点","zoneImage":["http://res.mymanba.cn/FqzxBoScyQw8OUnTqbWMzZD_uIUk","http://res.mymanba.cn/Fmr7r1GbYH8vNa_mMlYxJIyE5x2W","http://res.mymanba.cn/FjGGWyKH-QbszD5B7garS7_7QNPt","http://res.mymanba.cn/FvEzf7BDw81KQqtlHjw-4sFJ6WRh","http://res.mymanba.cn/Fr4Irc5fvlHO8x61dWN6tkH4wIIc","http://res.mymanba.cn/FjpdgaQ1gRJqHeEQPY-cL1-syVqK"],"publishTime":1514795179000,"guildId":4,"guildName":"番剧","upvoteNum":3,"favoriteNum":2,"commentNum":3,"lookNum":231,"phone":"18210135691","nickName":"秦时明月","sex":2,"userPhotoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","follow":false,"upvote":true,"favorite":false}]
         */

        private int userId;
        private String nickName;
        private String iconUrl;
        private String backgroundUrl;
        private String signName;
        private int sex;
        private int fenNum;
        private int followNum;
        private boolean follow;
        private Object imageList;
        private List<PersonInnerBean> zoneList;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getBackgroundUrl() {
            return backgroundUrl;
        }

        public void setBackgroundUrl(String backgroundUrl) {
            this.backgroundUrl = backgroundUrl;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getFenNum() {
            return fenNum;
        }

        public void setFenNum(int fenNum) {
            this.fenNum = fenNum;
        }

        public int getFollowNum() {
            return followNum;
        }

        public void setFollowNum(int followNum) {
            this.followNum = followNum;
        }

        public boolean isFollow() {
            return follow;
        }

        public void setFollow(boolean follow) {
            this.follow = follow;
        }

        public Object getImageList() {
            return imageList;
        }

        public void setImageList(Object imageList) {
            this.imageList = imageList;
        }

        public List<PersonInnerBean> getZoneList() {
            return zoneList;
        }

        public void setZoneList(List<PersonInnerBean> zoneList) {
            this.zoneList = zoneList;
        }

        public static class PersonInnerBean {
            /**
             * id : 11
             * userId : 7
             * zoneContent : 测试发帖
             * zoneImage : ["http://res.mymanba.cn/Fl0e5s0ddcVtZ1W3_upNr3ToHPh9","http://res.mymanba.cn/Fs471nq7Yq0t9MaM6bztixgMEh-n"]
             * publishTime : 1514103839000
             * guildId : 3
             * guildName : 情感&日常
             * upvoteNum : 0
             * favoriteNum : 0
             * commentNum : 0
             * lookNum : 1
             * phone : 18210135691
             * nickName : 秦时明月
             * sex : 2
             * userPhotoUrl : http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW
             * follow : false
             * upvote : false
             * favorite : false
             */

            private int id;
            private int userId;
            private String zoneContent;
            private long publishTime;
            private int guildId;
            private String guildName;
            private int upvoteNum;
            private int favoriteNum;
            private int commentNum;
            private int lookNum;
            private String phone;
            private String nickName;
            private int sex;
            private String userPhotoUrl;
            private boolean follow;
            private boolean upvote;
            private boolean favorite;
            private List<String> zoneImage;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getZoneContent() {
                return zoneContent;
            }

            public void setZoneContent(String zoneContent) {
                this.zoneContent = zoneContent;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public int getGuildId() {
                return guildId;
            }

            public void setGuildId(int guildId) {
                this.guildId = guildId;
            }

            public String getGuildName() {
                return guildName;
            }

            public void setGuildName(String guildName) {
                this.guildName = guildName;
            }

            public int getUpvoteNum() {
                return upvoteNum;
            }

            public void setUpvoteNum(int upvoteNum) {
                this.upvoteNum = upvoteNum;
            }

            public int getFavoriteNum() {
                return favoriteNum;
            }

            public void setFavoriteNum(int favoriteNum) {
                this.favoriteNum = favoriteNum;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public int getLookNum() {
                return lookNum;
            }

            public void setLookNum(int lookNum) {
                this.lookNum = lookNum;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getUserPhotoUrl() {
                return userPhotoUrl;
            }

            public void setUserPhotoUrl(String userPhotoUrl) {
                this.userPhotoUrl = userPhotoUrl;
            }

            public boolean isFollow() {
                return follow;
            }

            public void setFollow(boolean follow) {
                this.follow = follow;
            }

            public boolean isUpvote() {
                return upvote;
            }

            public void setUpvote(boolean upvote) {
                this.upvote = upvote;
            }

            public boolean isFavorite() {
                return favorite;
            }

            public void setFavorite(boolean favorite) {
                this.favorite = favorite;
            }

            public List<String> getZoneImage() {
                return zoneImage;
            }

            public void setZoneImage(List<String> zoneImage) {
                this.zoneImage = zoneImage;
            }
        }
    }
}
