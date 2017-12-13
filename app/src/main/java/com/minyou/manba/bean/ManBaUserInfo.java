package com.minyou.manba.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/2.
 */
public class ManBaUserInfo implements Parcelable{
    private String userID;
    private String nickName;
    private String phone;
    private int sex;    // 1男；2女
    private String interesting;
    private String photoUrl;
    private String roles;

    public ManBaUserInfo(){}

    protected ManBaUserInfo(Parcel in) {
        userID = in.readString();
        nickName = in.readString();
        phone = in.readString();
        sex = in.readInt();
        interesting = in.readString();
        photoUrl = in.readString();
        roles = in.readString();
    }

    public static final Creator<ManBaUserInfo> CREATOR = new Creator<ManBaUserInfo>() {
        @Override
        public ManBaUserInfo createFromParcel(Parcel in) {
            return new ManBaUserInfo(in);
        }

        @Override
        public ManBaUserInfo[] newArray(int size) {
            return new ManBaUserInfo[size];
        }
    };

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getInteresting() {
        return interesting;
    }

    public void setInteresting(String interesting) {
        this.interesting = interesting;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userID);
        parcel.writeString(nickName);
        parcel.writeString(phone);
        parcel.writeInt(sex);
        parcel.writeString(interesting);
        parcel.writeString(photoUrl);
        parcel.writeString(roles);
    }
}
