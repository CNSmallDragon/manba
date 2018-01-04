package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class ReplyCommentListResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : {"pageNo":1,"pageSize":10,"maxPageSize":100,"totalCount":2,"resultList":[{"commentId":19,"userId":null,"zoneId":20,"content":"楼中楼","commentTime":1515071187000,"parentId":3,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2,"upvote":null,"upvoteNum":null,"replyNum":null,"createTime":null},{"commentId":20,"userId":null,"zoneId":20,"content":"楼中楼22","commentTime":1515071196000,"parentId":3,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2,"upvote":null,"upvoteNum":null,"replyNum":null,"createTime":null}]}
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
         * resultList : [{"commentId":19,"userId":null,"zoneId":20,"content":"楼中楼","commentTime":1515071187000,"parentId":3,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2,"upvote":null,"upvoteNum":null,"replyNum":null,"createTime":null},{"commentId":20,"userId":null,"zoneId":20,"content":"楼中楼22","commentTime":1515071196000,"parentId":3,"commentUserId":7,"photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","nickName":"秦时明月","sex":2,"upvote":null,"upvoteNum":null,"replyNum":null,"createTime":null}]
         */

        private int pageNo;
        private int pageSize;
        private int maxPageSize;
        private int totalCount;
        private List<ReplyCommentListBean> resultList;

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

        public List<ReplyCommentListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ReplyCommentListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ReplyCommentListBean {
            /**
             * commentId : 19
             * userId : null
             * zoneId : 20
             * content : 楼中楼
             * commentTime : 1515071187000
             * parentId : 3
             * commentUserId : 7
             * photoUrl : http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW
             * nickName : 秦时明月
             * sex : 2
             * upvote : null
             * upvoteNum : null
             * replyNum : null
             * createTime : null
             */

            private int commentId;
            private String userId;
            private int zoneId;
            private String content;
            private long commentTime;
            private int parentId;
            private int commentUserId;
            private String photoUrl;
            private String nickName;
            private int sex;
            private Object upvote;
            private Object upvoteNum;
            private Object replyNum;
            private Object createTime;

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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

            public Object getUpvote() {
                return upvote;
            }

            public void setUpvote(Object upvote) {
                this.upvote = upvote;
            }

            public Object getUpvoteNum() {
                return upvoteNum;
            }

            public void setUpvoteNum(Object upvoteNum) {
                this.upvoteNum = upvoteNum;
            }

            public Object getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(Object replyNum) {
                this.replyNum = replyNum;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }
        }
    }
}
