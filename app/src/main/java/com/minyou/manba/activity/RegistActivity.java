package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.minyou.manba.R;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.util.LogUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistActivity extends Activity {

    private static final String TAG = "RegistActivity";
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
    @BindView(R.id.et_phone)
    EditText etPhone;

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;
    private String inputNumber;
    private String etNichengStr;
    private String etMimaStr;
    private String etSmsStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        atvTitle.setTitle(getResources().getString(R.string.regist_welcome));
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
                if(checkLoginNum()){
                    startCountDown();
                    getSmsCode();
                }
                break;
            case R.id.tv_regist:
                break;
        }
    }

    private boolean checkLoginNum() {
        inputNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(inputNumber)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (inputNumber.length() != 11) {
            Toast.makeText(this, "请输入完整的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkRegist() {
        etNichengStr = etNicheng.getText().toString().trim();
        if(TextUtils.isEmpty(etNichengStr)){
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return false;
        }
        checkLoginNum();
        etSmsStr = etSms.getText().toString().trim();
        if(TextUtils.isEmpty(etSmsStr)){
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        etMimaStr = etMima.getText().toString().trim();
        etMimaStr = etMima.getText().toString();
        if (TextUtils.isEmpty(etMimaStr)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etMimaStr.length() < 6 || etMimaStr.length() > 16) {
            Toast.makeText(this, "您输入的密码不符合规范，请重新输入!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getSmsCode() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
//                .add(Appconstant.Config.LOGIN_YPTE,Appconstant.Config.LOGIN_YPTE_NORMAL)
                .add("phoneNumber", inputNumber)
                .build();
        Request request = new Request.Builder()
                .url(ManBaApi.HTTP_GET_SEND_SMS)
//                .header("Accept","*/*")
//                .addHeader("Authorization",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.TOKEN))
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String requestStr = response.body().string();

                LogUtil.d(TAG, "-----response---------" + requestStr);
            }
        });
    }


}
