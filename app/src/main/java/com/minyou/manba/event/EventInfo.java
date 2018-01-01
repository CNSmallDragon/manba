package com.minyou.manba.event;

/**
 * Created by luchunhao on 2017/12/17.
 */
public class EventInfo {

    public static final int REGIST_RETURN = 1000;      //注册成功后返回
    public static final int LOGIN_WEIXIN = 1001;      //微信登陆后返回
    public static final int SETTING_USER_INFO = 1002;      //个人资料修改信息后返回
    public static final int ZONE_UPDATE_ZAN = 1003;      //更新首页赞按钮变化

    private int type;
    private Object data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
