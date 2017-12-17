package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.requestModel.RegistRequestModel;
import com.minyou.manba.network.responseModel.RegistResponseModel;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.util.LogUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by luchunhao on 2017/12/14.
 */
public class BindingPhoneActivity extends BaseActivity {

    private static final String TAG = "BindingPhoneActivity";
    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.login_number)
    EditText loginNumber;
    @BindView(R.id.et_sms)
    EditText etSms;
    @BindView(R.id.tv_send_sms)
    TextView tvSendSms;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;
    private String inputNumber;

    private RegistRequestModel requestModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        atv_title.setTitle(getResources().getString(R.string.regist_binding_phone));
        Intent intent = getIntent();
        if(null != intent){
            requestModel = intent.getParcelableExtra(Appconstant.LOGIN_USER_INFO);
        }
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
                if(checkLoginNum() && !TextUtils.isEmpty(etSms.getText())){
                    postRegist();
                }
                break;
        }
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

    private boolean checkLoginNum() {
        inputNumber = loginNumber.getText().toString();
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

    private void postRegist() {
        OkHttpClient client = new OkHttpClient();
        //RequestBody body = RequestBodyUtils.getRequestBody(requestModel);
        RequestBody body = new FormBody.Builder()
                .add("phone",inputNumber)
                .add("qq", requestModel.getQq())
                .add("weixin", requestModel.getWeixin())
                .add("nickName", requestModel.getNickName())
                .add("sex", String.valueOf(requestModel.getSex()))
                .add("smsCode", etSms.getText().toString().trim())
                .build();
        Request request = new Request.Builder()
                .url(ManBaApi.HTTP_POST_REGIST)
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
                RegistResponseModel model = new Gson().fromJson(requestStr,RegistResponseModel.class);
                if(model.getCode() == 0){
                    // 注册成功
                    finish();
                }
                LogUtil.d(TAG, "-----postRegist---------" + requestStr);
            }
        });
    }

}
