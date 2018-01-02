package com.minyou.manba.network.resultModel;

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

        public static class CommentItemBean {
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
        }
    }
}
