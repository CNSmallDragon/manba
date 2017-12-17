package com.minyou.manba.network.responseModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luchunhao on 2017/12/10.
 */
public class UserLoginModel extends BaseResponseModel implements Parcelable{


    /**
     * photoUrl : null
     * nickName : test
     * sex : 0
     * userId : 3
     * mvpPin :
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1BSRU1JVU1fTUVNQkVSIl0sImlzcyI6Imh0dHA6Ly93d3cubXltYW5iYS5jbiIsImlhdCI6MTUxMzUxOTMwNywiZXhwIjoxNTEzNjA1NzA3fQ.n1Qs48IO4hRwePkV4XS_F1DyPYpz_OrUMYZ4UhoqCP85ql3XUUIGtqZl9tuvYTmk1FxoXNdBcWhgPkulhT7ebQ
     * refreshToken : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODIxMDEzNTY5MSIsInNjb3BlcyI6WyJST0xFX1JFRlJFU0hfVE9LRU4iXSwiaXNzIjoiaHR0cDovL3d3dy5teW1hbmJhLmNuIiwianRpIjoiYzMxMWM3MjQtNjMyNS00ZGY0LWEzOTYtOTg1YzQ2MzhlNWZiIiwiaWF0IjoxNTEzNTE5MzA3LCJleHAiOjE1MTM1MjI5MDd9.tTpeeMNMswTKEXP7mV7zBdrDIylA4i9Gv6STypoHJvr4aYL7gpa3_twENBtQpGQmXOvzdaXQ3LhUy4jhXpMAtw
     */

    private String photoUrl;
    private String nickName;
    private String sex;
    private String userId;
    private String mvpPin;
    private String token;
    private String refreshToken;

    protected UserLoginModel(Parcel in) {
        photoUrl = in.readString();
        nickName = in.readString();
        sex = in.readString();
        userId = in.readString();
        mvpPin = in.readString();
        token = in.readString();
        refreshToken = in.readString();
    }

    public static final Creator<UserLoginModel> CREATOR = new Creator<UserLoginModel>() {
        @Override
        public UserLoginModel createFromParcel(Parcel in) {
            return new UserLoginModel(in);
        }

        @Override
        public UserLoginModel[] newArray(int size) {
            return new UserLoginModel[size];
        }
    };

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMvpPin() {
        return mvpPin;
    }

    public void setMvpPin(String mvpPin) {
        this.mvpPin = mvpPin;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photoUrl);
        parcel.writeString(nickName);
        parcel.writeString(sex);
        parcel.writeString(userId);
        parcel.writeString(mvpPin);
        parcel.writeString(token);
        parcel.writeString(refreshToken);
    }
}

