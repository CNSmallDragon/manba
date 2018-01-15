package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by luchunhao on 2018/1/15.
 */

public class GuildMembersResultModel extends BaseResultModel {


    /**
     * detail : string
     * result : {"maxPageSize":0,"pageNo":0,"pageSize":0,"resultList":[{"birthday":"2018-01-15T11:56:26.821Z","fenNum":0,"followNum":0,"mvpPin":"string","nickName":"string","password":"string","phone":"string","photoUrl":"string","qq":"string","refreshToken":"string","roles":[{"role":"1,管理员,ADMIN","userId":0}],"sex":0,"signName":"string","token":"string","userId":0,"weibo":"string","weixin":"string"}],"totalCount":0}
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
         * maxPageSize : 0
         * pageNo : 0
         * pageSize : 0
         * resultList : [{"birthday":"2018-01-15T11:56:26.821Z","fenNum":0,"followNum":0,"mvpPin":"string","nickName":"string","password":"string","phone":"string","photoUrl":"string","qq":"string","refreshToken":"string","roles":[{"role":"1,管理员,ADMIN","userId":0}],"sex":0,"signName":"string","token":"string","userId":0,"weibo":"string","weixin":"string"}]
         * totalCount : 0
         */

        private int maxPageSize;
        private int pageNo;
        private int pageSize;
        private int totalCount;
        private List<GuildMemberBean> resultList;

        public int getMaxPageSize() {
            return maxPageSize;
        }

        public void setMaxPageSize(int maxPageSize) {
            this.maxPageSize = maxPageSize;
        }

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

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<GuildMemberBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<GuildMemberBean> resultList) {
            this.resultList = resultList;
        }

        public static class GuildMemberBean {
            /**
             * birthday : 2018-01-15T11:56:26.821Z
             * fenNum : 0
             * followNum : 0
             * mvpPin : string
             * nickName : string
             * password : string
             * phone : string
             * photoUrl : string
             * qq : string
             * refreshToken : string
             * roles : [{"role":"1,管理员,ADMIN","userId":0}]
             * sex : 0
             * signName : string
             * token : string
             * userId : 0
             * weibo : string
             * weixin : string
             */

            private String birthday;
            private int fenNum;
            private int followNum;
            private String mvpPin;
            private String nickName;
            private String password;
            private String phone;
            private String photoUrl;
            private String qq;
            private String refreshToken;
            private int sex;
            private String signName;
            private String token;
            private int userId;
            private String weibo;
            private String weixin;
            private List<RolesBean> roles;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
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

            public String getMvpPin() {
                return mvpPin;
            }

            public void setMvpPin(String mvpPin) {
                this.mvpPin = mvpPin;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getRefreshToken() {
                return refreshToken;
            }

            public void setRefreshToken(String refreshToken) {
                this.refreshToken = refreshToken;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getSignName() {
                return signName;
            }

            public void setSignName(String signName) {
                this.signName = signName;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getWeibo() {
                return weibo;
            }

            public void setWeibo(String weibo) {
                this.weibo = weibo;
            }

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }

            public List<RolesBean> getRoles() {
                return roles;
            }

            public void setRoles(List<RolesBean> roles) {
                this.roles = roles;
            }

            public static class RolesBean {
                /**
                 * role : 1,管理员,ADMIN
                 * userId : 0
                 */

                private String role;
                private int userId;

                public String getRole() {
                    return role;
                }

                public void setRole(String role) {
                    this.role = role;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }
            }

            public UserZanListResultModel.UserZanListInnerBean toUserZanListInnerBean(){
                UserZanListResultModel.UserZanListInnerBean userZanListInnerBean = new UserZanListResultModel.UserZanListInnerBean();
                userZanListInnerBean.setNickName(getNickName());
                userZanListInnerBean.setPhotoUrl(getPhotoUrl());
                userZanListInnerBean.setUserId(getUserId());

                return userZanListInnerBean;
            }
        }
    }
}
