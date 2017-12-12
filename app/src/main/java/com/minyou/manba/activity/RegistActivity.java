package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.minyou.manba.R;
import com.minyou.manba.ui.ActionTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends Activity {

    @BindView(R.id.atv_title)
    ActionTitleView atvTitle;
    @BindView(R.id.iv_regist)
    ImageView ivRegist;
    @BindView(R.id.et_nicheng)
    EditText etNicheng;
    @BindView(R.id.et_sms)
    EditText etSms;
    @BindView(R.id.tv_send_sms)
    TextView tvSendSms;
    @BindView(R.id.et_mima)
    EditText etMima;
    @BindView(R.id.rb_sex_man)
    RadioButton rbSexMan;
    @BindView(R.id.rb_sex_women)
    RadioButton rbSexWomen;
    @BindView(R.id.cb_dongman)
    CheckBox cbDongman;
    @BindView(R.id.cb_manhua)
    CheckBox cbManhua;
    @BindView(R.id.cb_youxi)
    CheckBox cbYouxi;
    @BindView(R.id.cb_xiaoshuo)
    CheckBox cbXiaoshuo;
    @BindView(R.id.cb_cosplay)
    CheckBox cbCosplay;
    @BindView(R.id.cb_tongren)
    CheckBox cbTongren;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        atvTitle.setTitle(getResources().getString(R.string.regist));
    }


    // 启动倒计时
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startCountDown() {
        // 设置按钮为不可点击，并修改显示背景
        tvSendSms.setEnabled(false);
        tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_grey_8));
        // 开始倒计时
        new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 刷新文字
                tvSendSms.setText(getResources().getString(R.string.reget_sms_code_countdown, millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                // 重置文字，并恢复按钮为可点击
                tvSendSms.setText(R.string.regist_getyanzhengma);
                tvSendSms.setEnabled(true);
                tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_bluefull));
            }
        }.start();
    }

    @OnClick({R.id.tv_send_sms, R.id.tv_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_sms:
                startCountDown();
                break;
            case R.id.tv_regist:
                break;
        }
    }
}
