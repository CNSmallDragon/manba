package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/25.
 */
public class ZoneDetailResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : {"id":7,"userId":2,"zoneContent":"测试内容1","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd1.jpg"],"publishTime":1506675017000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0,"shareNum":null,"phone":"18516145130","nickName":"王五","sex":1,"userPhotoUrl":""}
     */

    private ZoneDetailBean result;

    public ZoneDetailBean getResult() {
        return result;
    }

    public void setResult(ZoneDetailBean result) {
        this.result = result;
    }

    public static class ZoneDetailBean {
        /**
         * id : 7
         * userId : 2
         * zoneContent : 测试内容1
         * zoneImage : ["http://res.mymanba.cn/www.baidu.com/dd1.jpg"]
         * publishTime : 1506675017000
         * guildId : null
         * guildName : null
         * upvoteNum : 0
         * favoriteNum : 0
         * commentNum : 0
         * shareNum : null
         * phone : 18516145130
         * nickName : 王五
         * sex : 1
         * userPhotoUrl :
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
        private int shareNum;
        private String phone;
        private String nickName;
        private int sex;
        private String userPhotoUrl;
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

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
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

        public List<String> getZoneImage() {
            return zoneImage;
        }

        public void setZoneImage(List<String> zoneImage) {
            this.zoneImage = zoneImage;
        }
    }
}
