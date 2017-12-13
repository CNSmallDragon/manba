package com.minyou.manba.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.minyou.manba.Appconstant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/21.
 */
public class CommonUtil {


    /**
     * 从本地sharedPreference文件判断当前用户是否已经登陆
     *
     * @return
     */
    public static boolean isLogin() {
        boolean isLogin = SharedPreferencesUtil.getInstance().getBoolean(Appconstant.LOGIN_OR_NOT);
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


    /**
     * 获取屏幕宽度
     * @param context
     * @return px
     */
    public static int getScreenWidth(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return px
     */
    public static int getScreenHeight(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将long类型时间转换为String
     * @param time
     * @return   要转换成的类型 "yy/MM/dd HH:mm"
     */
    public static String transformTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }

}
