package com.minyou.manba.network.resultModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.minyou.manba.BR;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class CommentListResultModel extends BaseResultModel {

    /**
     * detail : null
     * result : {"pageNo":1,"pageSize":10,"maxPageSize":100,"totalCount":2,"resultList":[{"commentId":1,"userId":null,"zoneId":20,"content":"评论","commentTime":1514807598000,"parentId":0,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2},{"commentId":3,"userId":null,"zoneId":20,"content":"再来一条","commentTime":1514893056000,"parentId":0,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2}]}
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
         * totalCount : 2
         * resultList : [{"commentId":1,"userId":null,"zoneId":20,"content":"评论","commentTime":1514807598000,"parentId":0,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2},{"commentId":3,"userId":null,"zoneId":20,"content":"再来一条","commentTime":1514893056000,"parentId":0,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2}]
         */

        private int pageNo;
        private int pageSize;
        private int maxPageSize;
        private int totalCount;
        private List<CommentItemBean> resultList;

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

        public List<CommentItemBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<CommentItemBean> resultList) {
            this.resultList = resultList;
        }

        public static class CommentItemBean extends BaseObservable implements Parcelable{
            /**
             * commentId : 1
             * userId : null
             * zoneId : 20
             * content : 评论
             * commentTime : 1514807598000
             * parentId : 0
             * commentUserId : 7
             * photoUrl : http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW
             * nickName : 秦时明月
             * sex : 2
             * upvote   是否点赞
             * upvoteNum    点赞数
             * replyNum     回复数
             * levelNum     楼层数
             */

            private int commentId;
            private Object userId;
            private int zoneId;
            private String content;
            private long commentTime;
            private int parentId;
            private int commentUserId;
            private String photoUrl;
            private String nickName;
            private int sex;
            private boolean upvote;
            private int upvoteNum;
            private int replyNum;
            private int levelNum;

            protected CommentItemBean(Parcel in) {
                commentId = in.readInt();
                zoneId = in.readInt();
                content = in.readString();
                commentTime = in.readLong();
                parentId = in.readInt();
                commentUserId = in.readInt();
                photoUrl = in.readString();
                nickName = in.readString();
                sex = in.readInt();
                upvote = in.readByte() != 0;
                upvoteNum = in.readInt();
                replyNum = in.readInt();
                levelNum = in.readInt();
            }

            public static final Creator<CommentItemBean> CREATOR = new Creator<CommentItemBean>() {
                @Override
                public CommentItemBean createFromParcel(Parcel in) {
                    return new CommentItemBean(in);
                }

                @Override
                public CommentItemBean[] newArray(int size) {
                    return new CommentItemBean[size];
                }
            };

            public int getLevelNum() {
                return levelNum;
            }

            public void setLevelNum(int levelNum) {
                this.levelNum = levelNum;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public int getZoneId() {
                return zoneId;
            }

            public void setZoneId(int zoneId) {
                this.zoneId = zoneId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(long commentTime) {
                this.commentTime = commentTime;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getCommentUserId() {
                return commentUserId;
            }

            public void setCommentUserId(int commentUserId) {
                this.commentUserId = commentUserId;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
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

            @Bindable
            public boolean isUpvote() {
                return upvote;
            }

            public void setUpvote(boolean upvote) {
                this.upvote = upvote;
                notifyPropertyChanged(BR.upvote);
            }

            public int getUpvoteNum() {
                return upvoteNum;
            }

            public void setUpvoteNum(int upvoteNum) {
                this.upvoteNum = upvoteNum;
            }

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(commentId);
                dest.writeInt(zoneId);
                dest.writeString(content);
                dest.writeLong(commentTime);
                dest.writeInt(parentId);
                dest.writeInt(commentUserId);
                dest.writeString(photoUrl);
                dest.writeString(nickName);
                dest.writeInt(sex);
                dest.writeByte((byte) (upvote ? 1 : 0));
                dest.writeInt(upvoteNum);
                dest.writeInt(replyNum);
                dest.writeInt(levelNum);
            }
        }
    }
}
