package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/22.
 */
public class SociationResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : {"pageNo":1,"pageSize":10,"maxPageSize":100,"totalCount":2,"resultList":[{"id":3,"guildName":"我的公会","memberNum":20,"liveness":null,"guildPhoto":null,"declaration":"测试","createUser":1,"createTime":1513947107000,"updateTime":null,"yn":0},{"id":4,"guildName":"测试","memberNum":50,"liveness":null,"guildPhoto":null,"declaration":"测试数据","createUser":7,"createTime":1513947156000,"updateTime":null,"yn":0}]}
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
         * resultList : [{"id":3,"guildName":"我的公会","memberNum":20,"liveness":null,"guildPhoto":null,"declaration":"测试","createUser":1,"createTime":1513947107000,"updateTime":null,"yn":0},{"id":4,"guildName":"测试","memberNum":50,"liveness":null,"guildPhoto":null,"declaration":"测试数据","createUser":7,"createTime":1513947156000,"updateTime":null,"yn":0}]
         */

        private int pageNo;
        private int pageSize;
        private int maxPageSize;
        private int totalCount;
        private List<SociationResultBean> resultList;

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

        public List<SociationResultBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<SociationResultBean> resultList) {
            this.resultList = resultList;
        }

        public static class SociationResultBean {
            /**
             * id : 3
             * guildName : 我的公会
             * memberNum : 20
             * liveness : null          活跃度
             * guildPhoto : null
             * declaration : 测试
             * createUser : 1
             * createTime : 1513947107000
             * updateTime : null
             * yn : 0
             */

            private int guildId;
            private String guildName;
            private int memberNum;
            private int liveness;
            private String guildPhoto;
            private String declaration;
            private int createUser;
            private int currentNum;
            private int zoneNum;
            private boolean guildMember;
            private boolean isChecked = false;

            public int getZoneNum() {
                return zoneNum;
            }

            public void setZoneNum(int zoneNum) {
                this.zoneNum = zoneNum;
            }

            public boolean isGuildMember() {
                return guildMember;
            }

            public void setGuildMember(boolean guildMember) {
                this.guildMember = guildMember;
            }

            public int getGuildId() {
                return guildId;
            }

            public void setGuildId(int guildId) {
                this.guildId = guildId;
            }

            public int getCurrentNum() {
                return currentNum;
            }

            public void setCurrentNum(int currentNum) {
                this.currentNum = currentNum;
            }

            public String getGuildName() {
                return guildName;
            }

            public void setGuildName(String guildName) {
                this.guildName = guildName;
            }

            public int getMemberNum() {
                return memberNum;
            }

            public void setMemberNum(int memberNum) {
                this.memberNum = memberNum;
            }

            public int getLiveness() {
                return liveness;
            }

            public void setLiveness(int liveness) {
                this.liveness = liveness;
            }

            public String getGuildPhoto() {
                return guildPhoto;
            }

            public void setGuildPhoto(String guildPhoto) {
                this.guildPhoto = guildPhoto;
            }

            public String getDeclaration() {
                return declaration;
            }

            public void setDeclaration(String declaration) {
                this.declaration = declaration;
            }

            public int getCreateUser() {
                return createUser;
            }

            public void setCreateUser(int createUser) {
                this.createUser = createUser;
            }


            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }
        }
    }
}
