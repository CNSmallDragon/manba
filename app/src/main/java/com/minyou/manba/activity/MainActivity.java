package com.minyou.manba.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityMainBinding;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends DataBindingBaseActivity implements OnClickListener {

    private static final String TAG = "MainActivity";
    private String[] ads = {"http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109135437541.png",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20170725104352981.jpg",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109145155437.gif",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109153125234.jpg"};

    private static final String PHONE = "username";
    private static final String PASSWORD = "password";

    private ActivityMainBinding binding;

    // 总倒计时时间
    private static final long MILLIS_IN_FUTURE = 5 * 1000;
    // 每次减去1秒
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private static final long WAIT_HOME = 3000;
    private CountDownTimer countDownTimer;

    private String picUrl;
    private String lastLoginType;

    @Override
    public void setContentViewAndBindData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        // 计算屏幕宽高
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        Random random = new Random();
        int nextInt = random.nextInt(10)%4;

        switch (nextInt){
            case 0:
                picUrl = ads[0];
                break;
            case 1:
                picUrl = ads[1];
                break;
            case 2:
                picUrl = ads[2];
                break;
            case 3:
                picUrl = ads[3];
                break;
        }
        binding.tvTime.setOnClickListener(this);
        Glide.with(this)
                .load(picUrl)
                .override(width,height)
                .into(binding.ivWelcome);
        // 获取所需权限
        getPermission();
        startCountDown();
    }

    private void  getPermission(){
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE
                }
        );

        if(isAllGranted){
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE
                },
                0
        );
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time:
                if(null != countDownTimer){
                    countDownTimer.cancel();
                }
                goHome();
                break;
        }
    }

    public void goHome(){
        // 判断用户是否登陆未登录跳转登陆界面
        if(UserManager.isLogin()){
            autoLogin();
            // 进入主界面
            //Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            //startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void autoLogin() {

//        HashMap<String,String> params = new HashMap<String,String>();
//
//
//        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_LOGIN, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
//            @Override
//            public void onReqSuccess(String result) {
//
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//
//            }
//        });

        OkHttpClient client = new OkHttpClient();
        RequestBody body = null;
        lastLoginType = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE);
        String qqId = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_QQ_ID);
        String wxId = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_WEIXIN_ID);

        if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("2") && !TextUtils.isEmpty(qqId)){
            // qq登陆,
            body = new FormBody.Builder()
                    .add("qqCode", qqId)
                    .build();
            lastLoginType = "2";
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("3") && !TextUtils.isEmpty(wxId)){
            // 微信登陆,
            body = new FormBody.Builder()
                    .add("weiXin", wxId)
                    .build();
            lastLoginType = "3";
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("1")){
            // 用户名密码登陆
            body = new FormBody.Builder()
                    .add(PHONE, SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_PHONE))
                    .add(PASSWORD, SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_PWD))
                    .build();
            lastLoginType = "1";

        }else{
            // 未登录
        }
        Request request = new Request.Builder()
                .url(ManBaApi.LOGINT_URL)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //SharedPreferencesUtil.getInstance(LoginActivity.this.getApplicationContext()).putSP(Appconstant.User.USER_ID, openId);
                String requestStr = response.body().string();
                LogUtil.d(TAG, "-----response---------" + requestStr);
                final UserLoginResultModel userLoginModelResult = new Gson().fromJson(requestStr,UserLoginResultModel.class);
                if(userLoginModelResult.getCode().equals("0")){  // 成功
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE,lastLoginType);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_INFO,requestStr);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_ID,userLoginModelResult.getResult().getUserId()+"");
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN,"Manba " + userLoginModelResult.getResult().getToken());
                    // 进入主界面
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else if(userLoginModelResult.getCode().equals("15")){      // 密码错误 400?15
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else if(userLoginModelResult.getCode().equals("14")){  // 用户不存在
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });

    }

    // 启动倒计时
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startCountDown() {
        // 开始倒计时
        countDownTimer = new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onTick(long millisUntilFinished) {
                // 刷新文字
                binding.tvTime.setText(getResources().getString(R.string.home_pass, millisUntilFinished / COUNT_DOWN_INTERVAL - 1));
            }

            @Override
            public void onFinish() {
                goHome();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
