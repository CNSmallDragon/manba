package com.minyou.manba.network.resultModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.minyou.manba.BR;

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

    public static class ZoneDetailBean extends BaseObservable{
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
        private boolean follow;
        private boolean upvote;
        private boolean favorite;

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

        @Bindable
        public int getUpvoteNum() {
            return upvoteNum;
        }

        public void setUpvoteNum(int upvoteNum) {
            this.upvoteNum = upvoteNum;
            notifyPropertyChanged(BR.upvoteNum);
        }

        @Bindable
        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
            notifyPropertyChanged(BR.favoriteNum);
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

        public boolean isFollow() {
            return follow;
        }

        public void setFollow(boolean follow) {
            this.follow = follow;
        }

        @Bindable
        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
            notifyPropertyChanged(BR.favorite);
        }

        @Bindable
        public boolean isUpvote() {
            return upvote;
        }

        public void setUpvote(boolean upvote) {
            this.upvote = upvote;
            notifyPropertyChanged(BR.upvote);

        }

        public ZoneListResultModel.ResultBean.ZoneListBean toZoneListBean(){
            ZoneListResultModel.ResultBean.ZoneListBean zoneListBean = new ZoneListResultModel.ResultBean.ZoneListBean();

            zoneListBean.setNickName(getNickName());
            zoneListBean.setSex(getSex());
            zoneListBean.setCommentNum(getCommentNum());
            zoneListBean.setFavoriteNum(getFavoriteNum());
            zoneListBean.setGuildId(getGuildId());
            zoneListBean.setGuildName(getGuildName());
            zoneListBean.setId(getId());
            zoneListBean.setUserId(getUserId());
            zoneListBean.setZoneContent(getZoneContent());
            zoneListBean.setZoneImage(getZoneImage());
            zoneListBean.setPublishTime(getPublishTime());
            zoneListBean.setUpvoteNum(getUpvoteNum());
            zoneListBean.setFavoriteNum(getFavoriteNum());
            zoneListBean.setPhone(getPhone());
            zoneListBean.setUserPhotoUrl(getUserPhotoUrl());
            zoneListBean.setFollow(isFollow());

            return zoneListBean;
        }

    }
}
