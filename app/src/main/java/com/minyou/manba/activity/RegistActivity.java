package com.minyou.manba.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityRegistBinding;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.resultModel.RegistResultModel;
import com.minyou.manba.network.resultModel.UserLoginModel;
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

public class RegistActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "RegistActivity";

    private ActivityRegistBinding binding;

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 60 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;
    private static final int REGIST_ERROR = 100;
    private static final int REGIST_SUCCESS = 101;
    private static final String PHONE = "username";
    private static final String PASSWORD = "password";
    private String inputNumber;
    private String etNichengStr;
    private String etMimaStr;
    private String etSmsStr;

    private int sex = 0;        // 2女1男

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  REGIST_ERROR:
                    Toast.makeText(RegistActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case REGIST_SUCCESS:
                    // 注册成功
                    // 将用户信息存入本地
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_PHONE,inputNumber);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_PWD,etMimaStr);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE,"1");
                    // 调用登陆接口获取用户信息
                    login();
                    break;
            }
        }
    };

    // 注册成功后登陆
    private void login() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(PHONE, inputNumber)
                .add(PASSWORD, etMimaStr)
                .build();
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
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_INFO,requestStr);
                    // 注册登录完成后跳转首页
                    Intent intent = new Intent(RegistActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_regist);

        initListener();
    }



    private void initListener() {

        binding.tvSendSms.setOnClickListener(this);
        binding.tvRegist.setOnClickListener(this);

        binding.cbDisplayPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (TextUtils.isEmpty(binding.etMima.getText())) {
                    return;
                }
                if (isChecked) {
                    binding.etMima.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    binding.etMima.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // 将光标移动到最后
                binding.etMima.setSelection(binding.etMima.getText().toString().length());
            }
        });
    }


    // 启动倒计时
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startCountDown() {
        // 设置按钮为不可点击，并修改显示背景
        binding.tvSendSms.setEnabled(false);
        binding.tvSendSms.setBackground(getDrawable(R.drawable.shape_borderline_grey_8));
        // 开始倒计时
        new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
            @SuppressLint("StringFormatMatches")
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
                if(checkRegist()){
                    postRegist();
                }
                break;
        }
    }


    private void postRegist() {
        OkHttpClient client = new OkHttpClient();
//        RegistRequestModel requestModel = new RegistRequestModel();
//        requestModel.setPhone(inputNumber);
//        requestModel.setPassword(etMimaStr);
//        requestModel.setNickName(etNichengStr);
//        requestModel.setSex(sex);
//        requestModel.setSmsCode(etSmsStr);
//        RequestBody body = RequestBodyUtils.getRequestBody(requestModel);
        RequestBody body = new FormBody.Builder()
                .add("phone",inputNumber)
                .add("password", etMimaStr)
                .add("nickName", etNichengStr)
                .add("sex", String.valueOf(sex))
                .add("smsCode", etSmsStr)
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
                Message message = Message.obtain();
                String requestStr = response.body().string();
                RegistResultModel model = new Gson().fromJson(requestStr,RegistResultModel.class);
                if(model.getCode().equals("0")){
                    // 注册成功
                    message.what = REGIST_SUCCESS;
                    message.obj = model.getResult();
                }else if(model.getCode().equals("18")){    // 验证码错误
                    message.what = REGIST_ERROR;
                    message.obj = model.getMsg();
                }else if(model.getCode().equals("21")){    // 用户已存在
                    message.what = REGIST_ERROR;
                    message.obj = model.getMsg();
                }
                handler.sendMessage(message);
                LogUtil.d(TAG, "-----postRegist---------" + requestStr);
            }
        });
    }

    private boolean checkLoginNum() {
        inputNumber = binding.etPhone.getText().toString();
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
        etNichengStr = binding.etNicheng.getText().toString().trim();
        if(TextUtils.isEmpty(etNichengStr)){
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return false;
        }
        checkLoginNum();
        etSmsStr = binding.etSms.getText().toString().trim();
        if(TextUtils.isEmpty(etSmsStr)){
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        etMimaStr = binding.etMima.getText().toString().trim();
        etMimaStr = binding.etMima.getText().toString();
        if (TextUtils.isEmpty(etMimaStr)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etMimaStr.length() < 6 || etMimaStr.length() > 16) {
            Toast.makeText(this, "您输入的密码不符合规范，请重新输入!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 校验性别
        sex = binding.rbSexMan.isChecked()?1:binding.rbSexWomen.isChecked()?2:0;
        if(sex == 0){
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
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
