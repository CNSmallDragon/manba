package com.minyou.manba.manager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.util.SharedPreferencesUtil;

/**
 * Created by luchunhao on 2017/12/19.
 */
public class UserManager {

    public static boolean isLogin(){
        String loginType = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE);
        String phone = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_PHONE);
        if(loginType.equals("1")){  // 正常登陆
            String userID = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
            String token = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.TOKEN);
            if(!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(token) ){
                return true;
            }
        }
        if(loginType.equals("2")){  // qq登陆
            String qqInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_QQ_INFO);
//            if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(qqInfo)){
            if(!TextUtils.isEmpty(qqInfo)){
                return true;
            }
        }
        if(loginType.equals("3")){  // 微信登陆
            String wxInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_WX_INFO);
            if(TextUtils.isEmpty(wxInfo)){
                wxInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_USER_INFO_WEIXIN);
            }
//            if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(wxInfo)){
            if(!TextUtils.isEmpty(wxInfo)){
                return true;
            }
        }
        return false;
    }

    public static String getUserId(){
        return SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
    }

    public static UserLoginResultModel getUserInfo(){
        String userInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_INFO);
        UserLoginResultModel userLoginResultModel = new Gson().fromJson(userInfo,UserLoginResultModel.class);
        return userLoginResultModel;
    }

    public static String getUserMvpPin(){
        String mvpPin="";
        mvpPin = getUserInfo().getResult().getMvpPin();
        return mvpPin;
    }
}
