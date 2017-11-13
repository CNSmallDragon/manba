package com.minyou.manba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/6.
 */
public class ItemInfo implements Serializable,Parcelable{
    private String userName;
    private String familyName;
    private String userPic;
    private long date;
    private String photoUrl;
    private String contentStr;
    private int sharedCount;
    private int zanCount;
    private int commentCount;

    public ItemInfo(Parcel parcel) {
        userName = parcel.readString();
        familyName = parcel.readString();
        userPic = parcel.readString();
        photoUrl = parcel.readString();
        contentStr = parcel.readString();
        date = parcel.readLong();
        sharedCount = parcel.readInt();
        zanCount = parcel.readInt();
        commentCount = parcel.readInt();
    }

    public ItemInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public int getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(int sharedCount) {
        this.sharedCount = sharedCount;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(familyName);
        parcel.writeString(userPic);
        parcel.writeString(photoUrl);
        parcel.writeString(contentStr);
        parcel.writeLong(date);
        parcel.writeInt(sharedCount);
        parcel.writeInt(zanCount);
        parcel.writeInt(commentCount);
    }

    public static final Creator<ItemInfo> CREATOR = new Creator<ItemInfo>() {
        @Override
        public ItemInfo createFromParcel(Parcel parcel) {
            return new ItemInfo(parcel);
        }

        @Override
        public ItemInfo[] newArray(int i) {
            return new ItemInfo[i];
        }
    };
}
