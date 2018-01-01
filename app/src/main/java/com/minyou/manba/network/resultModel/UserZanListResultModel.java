package com.minyou.manba.network.resultModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class UserZanListResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : [{"userId":7,"mvpPin":null,"phone":null,"nickName":"秦时明月","photoUrl":"http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW","password":null,"weibo":null,"weixin":null,"qq":null,"birthday":null,"signName":null,"sex":2,"token":null,"refreshToken":null,"roles":null},{"userId":9,"mvpPin":null,"phone":null,"nickName":"测试","photoUrl":"","password":null,"weibo":null,"weixin":null,"qq":null,"birthday":null,"signName":null,"sex":1,"token":null,"refreshToken":null,"roles":null}]
     */

    private List< UserZanListInnerBean> result;

    public List< UserZanListInnerBean> getResult() {
        return result;
    }

    public void setResult(List< UserZanListInnerBean> result) {
        this.result = result;
    }

    public static class  UserZanListInnerBean {
        /**
         * userId : 7
         * mvpPin : null
         * phone : null
         * nickName : 秦时明月
         * photoUrl : http://res.mymanba.cn/FvFLrq8XzCJmjZ-zrEaYn23ttRUW
         * password : null
         * weibo : null
         * weixin : null
         * qq : null
         * birthday : null
         * signName : null
         * sex : 2
         * token : null
         * refreshToken : null
         * roles : null
         */

        private int userId;
        private String mvpPin;
        private String phone;
        private String nickName;
        private String photoUrl;
        private String password;
        private String weibo;
        private String weixin;
        private String qq;
        private String birthday;
        private String signName;
        private int sex;
        private String token;
        private String refreshToken;
        private String roles;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMvpPin() {
            return mvpPin;
        }

        public void setMvpPin(String mvpPin) {
            this.mvpPin = mvpPin;
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

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }
    }
}
