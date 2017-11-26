package com.minyou.manba.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.minyou.manba.R;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.ui.MyScrollView;
import com.minyou.manba.ui.ScrollViewListener;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.LogUtil;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/25.
 */
public class PersonContentActivity extends Activity {

    private static final String TAG = "PersonContentActivity";
    private Unbinder unbinder;
    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.scrollview)
    MyScrollView mScrollView;
    @BindDimen(R.dimen.person_bg_height)
    float person_bg_height;         // 背景图片高度
    @BindDimen(R.dimen.common_title_height)
    float common_title_height;      // actionTitle高度
    @BindView(R.id.iv_person_pic)
    ImageView iv_person_pic;

    int statusBarHeight;            // 状态栏高度
    RelativeLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_content);
        unbinder = ButterKnife.bind(this);

        // 设置沉浸式状态栏
        statusBarHeight = CommonUtil.getStatusBarHeight(getApplicationContext());
        params = (RelativeLayout.LayoutParams) atv_title.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        atv_title.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();
    }

    private void initView() {

        final ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        final View statusBarView = new View(getWindow().getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);

        mScrollView.setOnScrollChangedListener(new ScrollViewListener() {
            @Override
            public void onScroll(MyScrollView scrollView, float x, float y, float oldx, float oldy) {
                LogUtil.d(TAG, "y=========" + y);
                if (y >= person_bg_height - statusBarHeight - common_title_height) {
                    atv_title.setBackgroundColor(Color.WHITE);
                    statusBarView.setBackgroundColor(Color.BLACK);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    // 设置标题
                    atv_title.setTitle("124");
                } else {
                    atv_title.setBackground(null);
                    statusBarView.setBackgroundColor(Color.TRANSPARENT);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    atv_title.setTitle("");
                    //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //透明状态栏
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        //透明导航栏
                        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    }
                }
            }
        });

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
