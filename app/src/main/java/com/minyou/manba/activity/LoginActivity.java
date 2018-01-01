package com.minyou.manba.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.bean.ManBaUserInfo;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.fragment.MineFragment;
import com.minyou.manba.model.LoginActivityModel;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.resultModel.QQResponseModel;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String NUMBER = "number";
    private static final String PHONE = "username";
    private static final String PASSWORD = "password";
    private static final int QQLOGIN = 1001;
    private static final int WXLOGIN = 1002;
    private static final int SINALOGIN = 1003;
    private static final int PASSWORD_ERROR = 400;

    private Unbinder unbinder;

    @BindView(R.id.cb_display_pwd)
    CheckBox cb_display_pwd;
    @BindView(R.id.login_pwd)
    EditText login_pwd;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.login_number)
    EditText login_number;
    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    private Tencent mTencent;
    @BindView(R.id.tv_qq)
    TextView tv_qq;
    @BindView(R.id.tv_sign_up)
    TextView tv_sign_up;

    public static String mAppid;
    private String inputNumber;
    private String inputPWD;
    public MyIUiListener myIUiListener;
    public ManBaUserInfo mUserInfo;

    private LoginActivityModel model;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case QQLOGIN:
                    ManBaUserInfo userInfo = (ManBaUserInfo) msg.obj;
                    Intent data = new Intent();
                    data.putExtra("nickname", data.getStringExtra("nickname"));
                    data.putExtra("figureurl_qq_2", data.getStringExtra("figureurl_qq_2"));
                    setResult(MineFragment.LOGIN_CODE, data);
                    break;
                case PASSWORD_ERROR:
                    // 密码错误
                    Toast.makeText(LoginActivity.this, "您输入的密码有误，请重新输入!", Toast.LENGTH_SHORT).show();
                    login_pwd.setText("");
                    break;
            }
            super.handleMessage(msg);
        }
    };

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //setContentView(R.layout.activity_login);
//        //unbinder = ButterKnife.bind(this);
//    }

    private void initListener() {
        bt_login.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        cb_display_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (TextUtils.isEmpty(login_pwd.getText())) {
                    return;
                }
                if (isChecked) {
                    login_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    login_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // 将光标移动到最后
                login_pwd.setSelection(login_pwd.getText().toString().length());
            }
        });
    }


    @OnClick(R.id.tv_forget_pwd)
    public void forgetPwd() {
        if (checkLoginNum()) {
            tv_forget_pwd.setTextColor(Color.BLUE);
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            intent.putExtra(NUMBER, inputNumber);
            startActivity(intent);

        }
    }

    @OnClick(R.id.tv_sina)
    public void loginXinLang() {
        // TODO Auto-generated method stub

    }

    @OnClick(R.id.tv_weixin)
    public void loginWeiXin() {
        // TODO Auto-generated method stub
        IWXAPI mApi = WXAPIFactory.createWXAPI(this, Appconstant.WEIXIN_APP_ID, true);
        mApi.unregisterApp();
        mApi.registerApp(Appconstant.WEIXIN_APP_ID);
        if (null != mApi && mApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            mApi.sendReq(req);
        } else {
            Toast.makeText(LoginActivity.this, "未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {
        loading();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
//                .add(Appconstant.Config.LOGIN_YPTE,Appconstant.Config.LOGIN_YPTE_NORMAL)
                .add(PHONE, inputNumber)
                .add(PASSWORD, inputPWD)
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
                UserLoginResultModel userLoginModel = new Gson().fromJson(requestStr, UserLoginResultModel.class);
                LogUtil.d(TAG, "-----response---------" + userLoginModel.getCode());
                if (userLoginModel != null && userLoginModel.getCode().equals("0")) {  // 成功
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE, "1");
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_PHONE, inputNumber);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_PWD, inputPWD);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_INFO,requestStr);
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_ID, userLoginModel.getResult().getUserId() + "");
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN, "Manba " + userLoginModel.getResult().getToken());
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN_REFRESH, userLoginModel.getResult().getRefreshToken());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (userLoginModel.getCode().equals("15")) {      // 密码错误 400?15
                    Message message = Message.obtain();
                    message.what = PASSWORD_ERROR;
                    handler.sendMessage(message);
                } else if (userLoginModel.getCode().equals("14")) {        // 用户不存在

                }
                cancelLoading();
            }
        });
    }

    private boolean checkLoginNum() {
        inputNumber = login_number.getText().toString();
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

    private boolean checkPassWord() {
        inputPWD = login_pwd.getText().toString();
        if (TextUtils.isEmpty(inputPWD)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (inputPWD.length() < 6 || inputPWD.length() > 16) {
            Toast.makeText(this, "您输入的密码不符合规范，请重新输入!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_forget_pwd.setTextColor(Color.GRAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: resultCode===" + resultCode);

        Tencent.onActivityResultData(requestCode, resultCode, data, myIUiListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        model = new LoginActivityModel();

        initListener();
    }

    /**
     * 微信登陆后返回，关闭当前activity
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWXReturn(EventInfo info) {
        if(null != info && info.getType() == EventInfo.LOGIN_WEIXIN){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @OnClick(R.id.tv_qq)
    public void loginQQ() {
        mAppid = Appconstant.QQ_APP_ID;
        mTencent = Tencent.createInstance(mAppid, LoginActivity.this);
        myIUiListener = new MyIUiListener();
        Log.d(TAG, "登陆开始-----------------");
        loading();
        mTencent.login(LoginActivity.this, "all", myIUiListener);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_login:
                if (checkLoginNum() && checkPassWord()) {
                    login();
                    //model.startLogin(inputNumber,inputPWD,this);
                    //MyHttpUtils.http_postRequest(ManBaApi.LOGINT_URL, RequestBodyUtils.getRequestBody(null),this);
                }
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }


    class MyIUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Log.d(TAG, "登陆完成-----------------");
            Log.d(TAG, response.toString());
            JSONObject jsonObject = (JSONObject) response;
            initOpenIdAndToken(jsonObject);
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "取消", Toast.LENGTH_SHORT).show();
        }

        private void initOpenIdAndToken(JSONObject jsonObject) {
            try {
                String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                Log.d(TAG, "openId-----------------" + openId);
                String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                if (!TextUtils.isEmpty(openId) && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)) {
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_QQ_ID, openId);
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void getUserInfo() {
            QQToken mQQToken = mTencent.getQQToken();
            UserInfo userInfo = new UserInfo(LoginActivity.this, mQQToken);
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Log.d(TAG, "获取用户信息-----------------");
                    Log.d(TAG, o.toString());
                    SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_QQ_INFO, o.toString());
                    final QQResponseModel qqModel = new Gson().fromJson(o.toString(), QQResponseModel.class);
                    qqModel.setOpenId(SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_QQ_ID));
                    // 根据第三方账号查询用户是否已经注册

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("qqCode", SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_QQ_ID))
                            .build();
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
                            String requestStr = response.body().string();
                            UserLoginResultModel userLoginModel = new Gson().fromJson(requestStr, UserLoginResultModel.class);
                            LogUtil.d(TAG, "-----response---------" + userLoginModel.getCode());
                            if (userLoginModel != null && userLoginModel.getCode().equals("0")) { // 成功
                                SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE, "2");
                                SharedPreferencesUtil.getInstance().putSP(Appconstant.User.USER_ID, userLoginModel.getResult().getUserId() + "");
                                SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN, "Manba " + userLoginModel.getResult().getToken());
                                SharedPreferencesUtil.getInstance().putSP(Appconstant.User.TOKEN_REFRESH, userLoginModel.getResult().getRefreshToken());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else if (userLoginModel != null && userLoginModel.getCode().equals("22")) {
                                Intent intnet = new Intent(LoginActivity.this, BindingPhoneActivity.class);
                                intnet.putExtra(Appconstant.User.USER_THRID_INFO, qqModel);
                                startActivity(intnet);
                            }
                            cancelLoading();
                            finish();

                            LogUtil.d("TAG", "11111-----response---------" + requestStr);

                        }
                    });

//                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_USER_INFO_QQ, o.toString());
//                    SharedPreferencesUtil.getInstance().putSP(Appconstant.LOGIN_LAST_TYPE, Appconstant.LOGIN_QQ);
//                    SharedPreferencesUtil.getInstance().putBoolean(Appconstant.LOGIN_OR_NOT, true);

                }

                @Override
                public void onError(UiError uiError) {
                    Log.d(TAG, "error-------getUserInfo----------");
                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "onCancel---------getUserInfo--------");
                }
            });
        }
    }

}
