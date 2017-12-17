package com.minyou.manba.network.responseModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.minyou.manba.bean.ManBaUserInfo;

/**
 * Created by luchunhao on 2017/12/13.
 */
public class UserInfoResponseModel extends BaseResponseModel{

    public UserLoginResultBean getUserLoginResultBean() {
        return result;
    }

    public void setUserLoginResultBean(UserLoginResultBean userLoginResultBean) {
        this.result = userLoginResultBean;
    }

    private UserLoginResultBean result;

    public static class UserLoginResultBean implements Parcelable{
        /**
         * userId : 3
         * phone : 18210135691
         * nickName : test
         * photoUrl : null
         * password : null
         * roles : null
         */

        private int userId;
        private String phone;
        private String nickName;
        private String photoUrl;
        private String password;
        private String roles;
        private int sex;


        protected UserLoginResultBean(Parcel in) {
            userId = in.readInt();
            phone = in.readString();
            nickName = in.readString();
            photoUrl = in.readString();
            password = in.readString();
            roles = in.readString();
            sex = in.readInt();
        }

        public static final Creator<UserLoginResultBean> CREATOR = new Creator<UserLoginResultBean>() {
            @Override
            public UserLoginResultBean createFromParcel(Parcel in) {
                return new UserLoginResultBean(in);
            }

            @Override
            public UserLoginResultBean[] newArray(int size) {
                return new UserLoginResultBean[size];
            }
        };

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public ManBaUserInfo toManBaUserInfo(){
            ManBaUserInfo info = new ManBaUserInfo();
            info.setNickName(this.nickName);
            info.setPhone(this.phone);
            info.setPhotoUrl(this.photoUrl);
            info.setSex(this.sex);
            info.setRoles(this.roles);

            return info;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(userId);
            parcel.writeString(phone);
            parcel.writeString(nickName);
            parcel.writeString(photoUrl);
            parcel.writeString(password);
            parcel.writeString(roles);
            parcel.writeInt(sex);
        }
    }



}
