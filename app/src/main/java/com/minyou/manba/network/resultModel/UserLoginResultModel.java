package com.minyou.manba.network.resultModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/20.
 */
public class UserLoginResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : {"userId":7,"mvpPin":"MVP-5f000cc1-5","phone":"18210135691","nickName":"测试","photoUrl":"","password":"","weibo":null,"weixin":null,"qq":null,"sex":1,"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1BSRU1JVU1fTUVNQkVSIl0sImlzcyI6Imh0dHA6Ly93d3cubXltYW5iYS5jbiIsImlhdCI6MTUxMzc2OTc5MywiZXhwIjoxNTEzODU2MTkzfQ.o7Na4u2DoPVQQrpu7xfxY29HbrJwetX-bvfGYXCkvJxWeGRe2hOWVfwQhs7_q01Y0TzvSu9I_QTOIWJWOxPBAQ","refreshToken":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1JFRlJFU0hfVE9LRU4iXSwiaXNzIjoiaHR0cDovL3d3dy5teW1hbmJhLmNuIiwianRpIjoiNWU1MDhhMTktZmQ2My00YmVmLTg1MmEtNjZmYjAyN2U0NThlIiwiaWF0IjoxNTEzNzY5NzkzLCJleHAiOjE1MTM3NzMzOTN9.CL7T8qo0iNWT2f4uOOA4xai9G6Bcb7HuZCq9JoUMou8tIt5rgwfPz-_CVW8QLe6rtMIjEPukQjp8AKf7-oPfgg","roles":[{"userId":7,"role":"PREMIUM_MEMBER"}]}
     */

//    @SerializedName("detail")
//    private Object detailX;
    private ResultBean result;

//    public Object getDetailX() {
//        return detailX;
//    }
//
//    public void setDetailX(Object detailX) {
//        this.detailX = detailX;
//    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {
        /**
         * userId : 7
         * mvpPin : MVP-5f000cc1-5
         * phone : 18210135691
         * nickName : 测试
         * photoUrl :
         * password :
         * weibo : null
         * weixin : null
         * qq : null
         * sex : 1
         * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1BSRU1JVU1fTUVNQkVSIl0sImlzcyI6Imh0dHA6Ly93d3cubXltYW5iYS5jbiIsImlhdCI6MTUxMzc2OTc5MywiZXhwIjoxNTEzODU2MTkzfQ.o7Na4u2DoPVQQrpu7xfxY29HbrJwetX-bvfGYXCkvJxWeGRe2hOWVfwQhs7_q01Y0TzvSu9I_QTOIWJWOxPBAQ
         * refreshToken : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1JFRlJFU0hfVE9LRU4iXSwiaXNzIjoiaHR0cDovL3d3dy5teW1hbmJhLmNuIiwianRpIjoiNWU1MDhhMTktZmQ2My00YmVmLTg1MmEtNjZmYjAyN2U0NThlIiwiaWF0IjoxNTEzNzY5NzkzLCJleHAiOjE1MTM3NzMzOTN9.CL7T8qo0iNWT2f4uOOA4xai9G6Bcb7HuZCq9JoUMou8tIt5rgwfPz-_CVW8QLe6rtMIjEPukQjp8AKf7-oPfgg
         * roles : [{"userId":7,"role":"PREMIUM_MEMBER"}]
         */

        private int userId;
        private String mvpPin;
        private String phone;
        private String nickName;
        private String photoUrl;
        private String password;
        private Object weibo;
        private Object weixin;
        private Object qq;
        private int sex;
        private String token;
        private String refreshToken;
        private List<RolesBean> roles;

        public ResultBean() {

        }

        public ResultBean(Parcel in) {
            userId = in.readInt();
            mvpPin = in.readString();
            phone = in.readString();
            nickName = in.readString();
            photoUrl = in.readString();
            password = in.readString();
            sex = in.readInt();
            token = in.readString();
            refreshToken = in.readString();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

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

        public Object getWeibo() {
            return weibo;
        }

        public void setWeibo(Object weibo) {
            this.weibo = weibo;
        }

        public Object getWeixin() {
            return weixin;
        }

        public void setWeixin(Object weixin) {
            this.weixin = weixin;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
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

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(userId);
            parcel.writeString(mvpPin);
            parcel.writeString(phone);
            parcel.writeString(nickName);
            parcel.writeString(photoUrl);
            parcel.writeString(password);
            parcel.writeInt(sex);
            parcel.writeString(token);
            parcel.writeString(refreshToken);
        }

        public static class RolesBean {
            /**
             * userId : 7
             * role : PREMIUM_MEMBER
             */

            private int userId;
            private String role;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }
        }
    }
}
