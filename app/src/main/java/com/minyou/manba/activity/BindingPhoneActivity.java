package com.minyou.manba.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityBindingPhoneBinding;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.resultModel.QQResponseModel;
import com.minyou.manba.network.resultModel.RegistResultModel;
import com.minyou.manba.network.resultModel.UserLoginModel;
import com.minyou.manba.network.resultModel.WeiXinResponseModel;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.io.IOException;

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
public class BindingPhoneActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "BindingPhoneActivity";

    private ActivityBindingPhoneBinding binding;

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;
    private String inputNumber;

    private Object object;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_phone);
        Intent intent = getIntent();
        if(null != intent){
            object = intent.getParcelableExtra(Appconstant.User.USER_THRID_INFO);
        }
        initListener();
    }

    private void initListener() {
        binding.tvSendSms.setOnClickListener(this);
        binding.tvRegist.setOnClickListener(this);
    }


    // 启动倒计时
    @SuppressLint("StringFormatMatches")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startCountDown() {
        // 设置按钮为不可点击，并修改显示背景
        binding.tvSendSms.setEnabled(false);
        binding.tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_grey_8));
        // 开始倒计时
        new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                // 刷新文字
                binding.tvSendSms.setText(getResources().getString(R.string.reget_sms_code_countdown, millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                // 重置文字，并恢复按钮为可点击
                binding.tvSendSms.setText(R.string.regist_getyanzhengma);
                binding.tvSendSms.setEnabled(true);
                binding.tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_bluefull));
            }
        }.start();
    }

    private boolean checkLoginNum() {
        inputNumber = binding.loginNumber.getText().toString();
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
        RequestBody body = null;
        if(object instanceof QQResponseModel){
            QQResponseModel qqResponseModel = (QQResponseModel) object;
            body = new FormBody.Builder()
                    .add("phone",inputNumber)
                    .add("nickName", qqResponseModel.getNickname())
                    .add("sex", qqResponseModel.getGender().equals("男") ? "1" : qqResponseModel.getGender().equals("女") ? "2" : "0")
                    .add("qq", qqResponseModel.getOpenId())
                    .add("smsCode", binding.etSms.getText().toString().trim())
//                    .add("photoUrl", qqResponseModel.getFigureurl_qq_2())
                    .build();
            SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE, "2");
        }else if(object instanceof WeiXinResponseModel){
            WeiXinResponseModel weiXinResponseModel = (WeiXinResponseModel) object;
            body = new FormBody.Builder()
                    .add("phone",inputNumber)
                    .add("nickName", weiXinResponseModel.getNickname())
                    .add("sex", weiXinResponseModel.getSex()+"")
                    .add("weixin", weiXinResponseModel.getOpenid())
                    .add("smsCode", binding.etSms.getText().toString().trim())
//                    .add("photoUrl", weiXinResponseModel.getHeadimgurl())
                    .build();
            SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE, "3");
        }

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
                SharedPreferencesUtil.getInstance().removeSP(Appconstant.LOGIN_LAST_TYPE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String requestStr = response.body().string();
                RegistResultModel model = new Gson().fromJson(requestStr,RegistResultModel.class);
                if(model.getCode().equals("0")){
                    // 注册成功
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_PHONE,inputNumber);
                    login();
                }else if(model.getCode().equals("21")){     //用户已存在

                }
                LogUtil.d(TAG, "-----postRegist---------" + requestStr);
            }
        });
    }

    // 注册成功后登陆
    private void login() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = null;
        if("2".equals(SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE))){
            body = new FormBody.Builder()
                    .add("qqCode", SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_QQ_ID))
                    .build();
        }else if("3".equals(SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE))){
            body = new FormBody.Builder()
                    .add("weiXin", SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_WEIXIN_ID))
                    .build();
        }
        Request request = new Request.Builder()
                .url(ManBaApi.LOGINT_URL)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //SharedPreferencesUtil.getInstance(LoginActivity.this.getApplicationContext()).putSP(Appconstant.User.USER_ID, openId);
                String requestStr = response.body().string();
                LogUtil.d(TAG, "-----response---------" + requestStr);
                UserLoginModel userLoginModel = new Gson().fromJson(requestStr,UserLoginModel.class);
                if(userLoginModel.getCode().equals("0")){  // 成功
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_ID, userLoginModel.getUserId());
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN, "Manba " + userLoginModel.getToken());
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN_REFRESH, userLoginModel.getRefreshToken());
                    // 注册登录完成后跳转首页
                    Intent intent = new Intent(BindingPhoneActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_sms:
                if(checkLoginNum()){
                    startCountDown();
                    getSmsCode();
                }
                break;
            case R.id.tv_regist:
                if(TextUtils.isEmpty(binding.etSms.getText())){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(checkLoginNum() && !TextUtils.isEmpty(binding.etSms.getText())){
                    postRegist();
                }
                break;
        }
    }
}
