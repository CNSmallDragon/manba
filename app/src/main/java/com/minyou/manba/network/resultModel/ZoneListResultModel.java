package com.minyou.manba.network.resultModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/19.
 */
public class ZoneListResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : {"pageNo":1,"pageSize":10,"maxPageSize":100,"totalCount":0,"resultList":[{"id":1,"userId":1,"zoneContent":"一个十年门户老编的哀叹：被今日头条革了命","zoneImage":["http://res.mymanba.cn/www.baidu.com/1.jpg"],"publishTime":1506666888000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":2,"userId":2,"zoneContent":"被赔\u201c葱\u201d的宾利车主现身 称赔\u201c葱\u201d大爷有点儿意思","zoneImage":["http://res.mymanba.cn/www.baidu.com/2.jpg"],"publishTime":1506666929000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":3,"userId":3,"zoneContent":"皇马续约阿森西奥至2023 年薪350万欧解约金5亿","zoneImage":["http://res.mymanba.cn/www.baidu.com/3.jpg"],"publishTime":1506666958000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":4,"userId":4,"zoneContent":"英国发现罗马时期铜制小狗雕像 伸舌头表情生动","zoneImage":["http://res.mymanba.cn/www.baidu.com/4.jpg"],"publishTime":1506666997000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":5,"userId":5,"zoneContent":"中国全面\u201c封杀\u201d比特币 外媒：戳破国际市场泡沫","zoneImage":["http://res.mymanba.cn/www.baidu.com/5.jpg"],"publishTime":1506667025000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":6,"userId":2,"zoneContent":"测试内容","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd.jpg"],"publishTime":1506674926000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":7,"userId":2,"zoneContent":"测试内容1","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd1.jpg"],"publishTime":1506675017000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":8,"userId":3,"zoneContent":"测试内容3","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd3.jpg"],"publishTime":1506676061000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":9,"userId":1,"zoneContent":"我的动态","zoneImage":null,"publishTime":1509958977000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":10,"userId":3,"zoneContent":"测试","zoneImage":null,"publishTime":1513601214000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0}]}
     */

    private ResultBean result;


    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * maxPageSize : 100
         * totalCount : 0
         * resultList : [{"id":1,"userId":1,"zoneContent":"一个十年门户老编的哀叹：被今日头条革了命","zoneImage":["http://res.mymanba.cn/www.baidu.com/1.jpg"],"publishTime":1506666888000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":2,"userId":2,"zoneContent":"被赔\u201c葱\u201d的宾利车主现身 称赔\u201c葱\u201d大爷有点儿意思","zoneImage":["http://res.mymanba.cn/www.baidu.com/2.jpg"],"publishTime":1506666929000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":3,"userId":3,"zoneContent":"皇马续约阿森西奥至2023 年薪350万欧解约金5亿","zoneImage":["http://res.mymanba.cn/www.baidu.com/3.jpg"],"publishTime":1506666958000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":4,"userId":4,"zoneContent":"英国发现罗马时期铜制小狗雕像 伸舌头表情生动","zoneImage":["http://res.mymanba.cn/www.baidu.com/4.jpg"],"publishTime":1506666997000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":5,"userId":5,"zoneContent":"中国全面\u201c封杀\u201d比特币 外媒：戳破国际市场泡沫","zoneImage":["http://res.mymanba.cn/www.baidu.com/5.jpg"],"publishTime":1506667025000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":6,"userId":2,"zoneContent":"测试内容","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd.jpg"],"publishTime":1506674926000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":7,"userId":2,"zoneContent":"测试内容1","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd1.jpg"],"publishTime":1506675017000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":8,"userId":3,"zoneContent":"测试内容3","zoneImage":["http://res.mymanba.cn/www.baidu.com/dd3.jpg"],"publishTime":1506676061000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":9,"userId":1,"zoneContent":"我的动态","zoneImage":null,"publishTime":1509958977000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0},{"id":10,"userId":3,"zoneContent":"测试","zoneImage":null,"publishTime":1513601214000,"guildId":null,"guildName":null,"upvoteNum":0,"favoriteNum":0,"commentNum":0}]
         */

        private int pageNo;
        private int pageSize;
        private int maxPageSize;
        private int totalCount;
        private List<ZoneListBean> resultList;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getMaxPageSize() {
            return maxPageSize;
        }

        public void setMaxPageSize(int maxPageSize) {
            this.maxPageSize = maxPageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ZoneListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ZoneListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ZoneListBean implements Parcelable {
            /**
             * id : 1
             * userId : 1
             * zoneContent : 一个十年门户老编的哀叹：被今日头条革了命
             * zoneImage : ["http://res.mymanba.cn/www.baidu.com/1.jpg"]
             * publishTime : 1506666888000
             * guildId : null
             * guildName : null
             * upvoteNum : 0
             * favoriteNum : 0
             * commentNum : 0
             * phone
             * nickName
             * sex
             * userPhotoUrl
             *
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
            private List<String> zoneImage;
            private String phone;
            private String nickName;
            private int sex;
            private String userPhotoUrl;

            protected ZoneListBean(Parcel in) {
                id = in.readInt();
                userId = in.readInt();
                zoneContent = in.readString();
                publishTime = in.readLong();
                guildId = in.readInt();
                guildName = in.readString();
                upvoteNum = in.readInt();
                favoriteNum = in.readInt();
                commentNum = in.readInt();
                zoneImage = in.createStringArrayList();
                phone = in.readString();
                nickName = in.readString();
                sex = in.readInt();
                userPhotoUrl = in.readString();
            }

            public static final Creator<ZoneListBean> CREATOR = new Creator<ZoneListBean>() {
                @Override
                public ZoneListBean createFromParcel(Parcel in) {
                    return new ZoneListBean(in);
                }

                @Override
                public ZoneListBean[] newArray(int size) {
                    return new ZoneListBean[size];
                }
            };

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

            public Object getGuildId() {
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

            public List<String> getZoneImage() {
                return zoneImage;
            }

            public void setZoneImage(List<String> zoneImage) {
                this.zoneImage = zoneImage;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeInt(userId);
                dest.writeString(zoneContent);
                dest.writeLong(publishTime);
                dest.writeInt(guildId);
                dest.writeString(guildName);
                dest.writeInt(upvoteNum);
                dest.writeInt(favoriteNum);
                dest.writeInt(commentNum);
                dest.writeStringList(zoneImage);
                dest.writeString(phone);
                dest.writeString(nickName);
                dest.writeInt(sex);
                dest.writeString(userPhotoUrl);
            }
        }
    }
}
