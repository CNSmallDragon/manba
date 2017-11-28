package com.minyou.manba.activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/28.
 */
public abstract class BaseActivity extends Activity {

    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 返回当前Activity布局ID
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 处理当前Activity布局view显示
     * @param savedInstanceState
     */
    public abstract void initView(Bundle savedInstanceState);



}
