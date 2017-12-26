package com.minyou.manba.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.minyou.manba.R;

/**
 * Created by luchunhao on 2017/12/26.
 */
public abstract class DataBindingBaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAllowFullScreen();                   // 屏幕相关设置
        //setStatusAndNavigation();               // 状态栏和导航栏相关
        setContentViewAndBindData();
    }

    protected void loading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void cancelLoading() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }

    public abstract void setContentViewAndBindData();

    /**
     * 屏幕设置
     */
    public void setAllowFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
    }

    /**
     * 状态栏和导航栏相关
     */
    public void setStatusAndNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
