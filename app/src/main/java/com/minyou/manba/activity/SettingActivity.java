package com.minyou.manba.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.minyou.manba.R;
import com.minyou.manba.ui.ActionTitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/3.
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.atv_title)
    ActionTitleView atvTitle;
    @BindView(R.id.iv_user_pic)
    ImageView ivUserPic;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_user_setting)
    LinearLayout llUserSetting;
    @BindView(R.id.switch_notify)
    Switch switchNotify;
    @BindView(R.id.ll_tongzhi_setting)
    LinearLayout llTongzhiSetting;
    @BindView(R.id.ll_huancun_setting)
    LinearLayout llHuancunSetting;
    @BindView(R.id.ll_qa_setting)
    LinearLayout llQaSetting;
    @BindView(R.id.ll_idea_fankui)
    LinearLayout llIdeaFankui;
    @BindView(R.id.ll_update_version)
    LinearLayout llUpdateVersion;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        atvTitle.setTitle(getResources().getString(R.string.my_shezhi));
    }

    @OnClick({R.id.ll_user_setting, R.id.ll_huancun_setting, R.id.ll_qa_setting, R.id.ll_idea_fankui, R.id.ll_update_version, R.id.ll_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_user_setting:
                break;
            case R.id.ll_huancun_setting:
                break;
            case R.id.ll_qa_setting:
                break;
            case R.id.ll_idea_fankui:
                break;
            case R.id.ll_update_version:
                break;
            case R.id.ll_about_us:
                break;
        }
    }
}
