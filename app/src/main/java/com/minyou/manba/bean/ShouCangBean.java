package com.minyou.manba.bean;

/**
 * Created by Administrator on 2017/12/2.
 */
public class ShouCangBean {

    private String user_pic;        // 用户头像地址
    private String user_name;
    private long shouc_time;
    private String pub_pic;         // 发布的图片地址
    private String pub_content;

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public long getShouc_time() {
        return shouc_time;
    }

    public void setShouc_time(long shouc_time) {
        this.shouc_time = shouc_time;
    }

    public String getPub_content() {
        return pub_content;
    }

    public void setPub_content(String pub_content) {
        this.pub_content = pub_content;
    }

    public String getPub_pic() {
        return pub_pic;
    }

    public void setPub_pic(String pub_pic) {
        this.pub_pic = pub_pic;
    }
}
