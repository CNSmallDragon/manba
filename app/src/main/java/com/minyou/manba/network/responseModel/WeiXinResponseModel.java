package com.minyou.manba.network.responseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/17.
 */
public class WeiXinResponseModel implements Parcelable {

    /**
     * openid : oWl-c0rQl1IcmCjC-Gf5MYbInhbw
     * nickname : 冷
     * sex : 1
     * language : zh_CN
     * city : Changping
     * province : Beijing
     * country : CN
     * headimgurl : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIGJeCvurxa6UcTpSiaB5vqoz7ica3T9ExTDCr6iadjiaVh6ppnSkhSvShxQK188m5ZEiamdkpYrluibD0g/0
     * privilege : []
     * unionid : ouluO0465uskRcM-f9SLAE5gf6IY
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    protected WeiXinResponseModel(Parcel in) {
        openid = in.readString();
        nickname = in.readString();
        sex = in.readInt();
        language = in.readString();
        city = in.readString();
        province = in.readString();
        country = in.readString();
        headimgurl = in.readString();
        unionid = in.readString();
    }

    public static final Creator<WeiXinResponseModel> CREATOR = new Creator<WeiXinResponseModel>() {
        @Override
        public WeiXinResponseModel createFromParcel(Parcel in) {
            return new WeiXinResponseModel(in);
        }

        @Override
        public WeiXinResponseModel[] newArray(int size) {
            return new WeiXinResponseModel[size];
        }
    };

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(openid);
        parcel.writeString(nickname);
        parcel.writeInt(sex);
        parcel.writeString(language);
        parcel.writeString(city);
        parcel.writeString(province);
        parcel.writeString(country);
        parcel.writeString(headimgurl);
        parcel.writeString(unionid);
    }
}
