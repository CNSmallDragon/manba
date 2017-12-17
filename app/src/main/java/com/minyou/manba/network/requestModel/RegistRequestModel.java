package com.minyou.manba.network.requestModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luchunhao on 2017/12/14.
 */
public class RegistRequestModel extends BaseRequestModel implements Parcelable{

    private String phone;
    private String password;
    private String nickName;
    private int sex;
    private String interesting;
    private String weibo;
    private String weixin;
    private String qq;
    private String smsCode;
    private String picUrl;

    public RegistRequestModel(){

    }

    public RegistRequestModel(Parcel in) {
        phone = in.readString();
        password = in.readString();
        nickName = in.readString();
        sex = in.readInt();
        interesting = in.readString();
        weibo = in.readString();
        weixin = in.readString();
        qq = in.readString();
        smsCode = in.readString();
        picUrl = in.readString();
    }

    public static final Creator<RegistRequestModel> CREATOR = new Creator<RegistRequestModel>() {
        @Override
        public RegistRequestModel createFromParcel(Parcel in) {
            return new RegistRequestModel(in);
        }

        @Override
        public RegistRequestModel[] newArray(int size) {
            return new RegistRequestModel[size];
        }
    };

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getInteresting() {
        return interesting;
    }

    public void setInteresting(String interesting) {
        this.interesting = interesting;
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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(phone);
        parcel.writeString(password);
        parcel.writeString(nickName);
        parcel.writeInt(sex);
        parcel.writeString(interesting);
        parcel.writeString(weibo);
        parcel.writeString(weixin);
        parcel.writeString(qq);
        parcel.writeString(smsCode);
        parcel.writeString(picUrl);
    }
}
