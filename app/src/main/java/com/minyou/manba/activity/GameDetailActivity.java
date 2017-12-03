package com.minyou.manba.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minyou.manba.R;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.ui.BorderTextView;
import com.minyou.manba.ui.MyScrollView;
import com.minyou.manba.ui.ScrollViewListener;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.LogUtil;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/2.
 */
public class GameDetailActivity extends BaseActivity {

    private static final String TAG = "GameDetailActivity";
    @BindView(R.id.btv_jianjie)
    BorderTextView btv_jianjie;
    @BindView(R.id.iv_game_bgpic)
    ImageView ivGameBgpic;
    @BindView(R.id.iv_game_pic)
    ImageView ivGamePic;
    @BindView(R.id.tv_game_name)
    TextView tvGameName;
    @BindView(R.id.tv_game_type)
    TextView tvGameType;
    @BindView(R.id.bt_install)
    Button btInstall;
    @BindView(R.id.btv_libao)
    BorderTextView btvLibao;
    @BindView(R.id.fl_game_content)
    FrameLayout flGameContent;
    @BindView(R.id.scrollview)
    MyScrollView scrollview;
    @BindView(R.id.atv_title)
    ActionTitleView atvTitle;
    @BindDimen(R.dimen.game_detail_bg_height)
    float game_detail_bg_height;         // 背景图片高度
    @BindDimen(R.dimen.common_title_height)
    float common_title_height;      // actionTitle高度

    int statusBarHeight;            // 状态栏高度
    RelativeLayout.LayoutParams params;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gamedetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // 设置沉浸式状态栏
        statusBarHeight = CommonUtil.getStatusBarHeight(getApplicationContext());
        params = (RelativeLayout.LayoutParams) atvTitle.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        atvTitle.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initListener();
    }

    private void initListener() {
        final ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        final View statusBarView = new View(getWindow().getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        scrollview.setOnScrollChangedListener(new ScrollViewListener() {
            @Override
            public void onScroll(MyScrollView scrollView, float x, float y, float oldx, float oldy) {
                LogUtil.d(TAG, "y=========" + y);
                if (y >= game_detail_bg_height - statusBarHeight - common_title_height) {
                    atvTitle.setBackgroundColor(Color.WHITE);
                    statusBarView.setBackgroundColor(Color.BLACK);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    // 设置标题
                    atvTitle.setTitle("游戏名");
                } else {
                    atvTitle.setBackground(null);
                    statusBarView.setBackgroundColor(Color.TRANSPARENT);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    atvTitle.setTitle("");
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

    @OnClick(R.id.btv_jianjie)
    public void onclick(View view) {
        if (view instanceof BorderTextView) {
            btv_jianjie = (BorderTextView) view;
            LogUtil.d(TAG, "-----------");
            btv_jianjie.isChecked(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
