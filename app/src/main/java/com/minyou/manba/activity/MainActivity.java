package com.minyou.manba.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";
    private String[] ads = {"http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109135437541.png",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20170725104352981.jpg",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109145155437.gif",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109153125234.jpg"};

    private static final String PHONE = "username";
    private static final String PASSWORD = "password";

    private static final long WAIT_HOME = 3000;
    private Unbinder unbinder;
    @BindView(R.id.iv_welcome)
    ImageView iv_welcome;

    private String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

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

        Glide.with(this)
                .load(picUrl)
                .override(width,height)
                .into(iv_welcome);

        // 延时3s进入主界面
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(WAIT_HOME);
                    goHome();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
    }

    public void goHome(){
        // 判断用户是否登陆未登录跳转登陆界面
        if(UserManager.isLogin()){
            autoLogin();
        }else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void autoLogin() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = null;
        final String lastLoginType = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE);
        String qqId = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_QQ_ID);
        String wxId = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_WEIXIN_ID);

        LogUtil.d(TAG, "lastLoginType--------------" + lastLoginType);
        if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("2") && !TextUtils.isEmpty(qqId)){
            // qq登陆,
            body = new FormBody.Builder()
                    .add("qqCode", qqId)
                    .build();
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("3") && !TextUtils.isEmpty(wxId)){
            // 微信登陆,
            body = new FormBody.Builder()
                    .add("weiXin", wxId)
                    .build();
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("1")){
            // 用户名密码登陆
            body = new FormBody.Builder()
                    .add(PHONE, SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_PHONE))
                    .add(PASSWORD, SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_PWD))
                    .build();

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
                LogUtil.d(TAG, "-----response---------" + userLoginModelResult.getCode());
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
                }
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
