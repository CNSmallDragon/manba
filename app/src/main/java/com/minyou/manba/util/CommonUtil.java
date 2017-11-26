package com.minyou.manba.util;

import android.content.Context;

import com.minyou.manba.Appconstant;

/**
 * Created by Administrator on 2017/11/21.
 */
public class CommonUtil {


    /**
     * 从本地sharedPreference文件判断当前用户是否已经登陆
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        boolean isLogin = SharedPreferencesUtil.getInstance(context).getBoolean(Appconstant.LOGIN_OR_NOT);
        return isLogin;
    }

    /**
     * 获取状态栏高度，返回px
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
