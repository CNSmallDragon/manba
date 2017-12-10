package com.minyou.manba;

import android.app.Application;

/**
 * Created by luchunhao on 2017/12/8.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static MyApplication mApplication;

    public static MyApplication getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }
}
