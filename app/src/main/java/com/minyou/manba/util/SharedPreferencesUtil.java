package com.minyou.manba.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.minyou.manba.MyApplication;

/**
 * Created by Administrator on 2017/11/2.
 */
public class SharedPreferencesUtil {

    public static final String mTAG = "manba";
    //创建一个写入器
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;

    //构造方法
    private SharedPreferencesUtil(Context context) {
        mPreferences = context.getSharedPreferences(mTAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    //单例模式
    public static SharedPreferencesUtil getInstance() {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(MyApplication.getInstance());
        }
        return mSharedPreferencesUtil;
    }

    //存入数据
    public void putSP(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }
    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    //获取数据
    public String getSP(String key) {
        return mPreferences.getString(key, "");
    }
    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    //移除数据
    public void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    // 清除所有数据
    public void removeAll(){
        mEditor.clear();
        mEditor.commit();
    }
}


