package com.minyou.manba.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.GameCenterActivity;
import com.minyou.manba.activity.HomeActivity;
import com.minyou.manba.activity.LoginActivity;
import com.minyou.manba.activity.MyWalletActivity;
import com.minyou.manba.activity.SettingActivity;
import com.minyou.manba.activity.ShouCangActivity;
import com.minyou.manba.activity.SociationDetailActivity;
import com.minyou.manba.bean.ManBaUserInfo;
import com.minyou.manba.event.MessageEvent;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.network.resultModel.UserInfoResponseModel;
import com.minyou.manba.util.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

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

public class MineFragment extends BaseFragment {

    private static final String TAG = "MineFragment";
    public static final int LOGIN_CODE = 100;
    public static final String REQUEST = "requestInfo";

    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.rl_after_login)
    RelativeLayout rl_after_login;
    @BindView(R.id.rl_before_login)
    RelativeLayout rl_before_login;
    @BindView(R.id.ll_after_login)
    LinearLayout ll_after_login;
    @BindView(R.id.iv_user_pic)
    ImageView user_pic;
    @BindView(R.id.tv_pub_time)
    TextView user_name;

    private RequestManager glideRequest;

    private ManBaUserInfo mUserInfo;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        glideRequest = Glide.with(getActivity());
        if (isLogin()) {
            initUserInfo(mUserInfo);
        } else {
            rl_after_login.setVisibility(View.GONE);
            ll_after_login.setVisibility(View.GONE);
            rl_before_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

    }

    @OnClick({R.id.mine_gonghui,R.id.mine_qianbao,R.id.mine_shoucang,R.id.mine_dongtai,R.id.mine_xiaoxi,R.id.mine_haiwan,R.id.mine_setting})
    public void itemOnClick(TextView button){
        Intent intent;
        switch (button.getId()){
            case R.id.mine_gonghui:
                // 我的公会
                Toast.makeText(getActivity(), "我的公会", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), SociationDetailActivity.class);
                intent.putExtra(Appconstant.FROM_WHERE,Appconstant.MINE2GONGHUI);
                startActivity(intent);
                break;
            case R.id.mine_qianbao:
                // 我的钱包
                intent = new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_shoucang:
                // 我的收藏
                intent = new Intent(getActivity(), ShouCangActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_dongtai:
                // 我的动态
                break;
            case R.id.mine_xiaoxi:
                // 消息
                ((HomeActivity)getActivity()).getRg_main().check(R.id.rb_jiequ);
                break;
            case R.id.mine_haiwan:
                // 嗨玩
                intent = new Intent(getActivity(), GameCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:
                // 设置
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 初始化个人信息
     * @param object
     */
    private void initUserInfo(Object object){
        if(object instanceof ManBaUserInfo){
            mUserInfo = (ManBaUserInfo) object;
            user_name.setText(mUserInfo.getNickName());
            if(!TextUtils.isEmpty(mUserInfo.getPhotoUrl())){
                glideRequest.load(mUserInfo.getPhotoUrl()).transform(new GlideCircleTransform(getActivity())).into(user_pic);
            }
        }else if(object instanceof UserInfoResponseModel){
            UserInfoResponseModel info = (UserInfoResponseModel) object;
            user_name.setText(info.getNickName());
            if(!TextUtils.isEmpty(info.getPhotoUrl())){
                glideRequest.load(info.getPhotoUrl()).transform(new GlideCircleTransform(getActivity())).into(user_pic);
            }
        }
        rl_after_login.setVisibility(View.VISIBLE);
        ll_after_login.setVisibility(View.VISIBLE);
        rl_before_login.setVisibility(View.GONE);
    }

    /**
     * 登陆后返回数据
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginReturn(MessageEvent messageEvent){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(messageEvent.getMessage());
            ManBaUserInfo mUserInfo = new ManBaUserInfo();
            mUserInfo.setNickName(jsonObject.get(Appconstant.LOGIN_WEIXIN_NAME).toString().trim());
            mUserInfo.setSex(Integer.parseInt(jsonObject.get(Appconstant.LOGIN_WEIXIN_SEX).toString().trim()));
            mUserInfo.setPhotoUrl(jsonObject.get(Appconstant.LOGIN_WEIXIN_PHOTO).toString().trim());
            initUserInfo(mUserInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断用户是否登陆
     * @return
     */
    private boolean isLogin() {
        String userInfo = "";
        String lastLoginType = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE);
        LogUtil.d(TAG, "lastLoginType--------------" + lastLoginType);
        if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals(Appconstant.LOGIN_QQ)){
            // qq登陆,
            String qq_id = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_QQ_ID);
            // TODO 网络请求登陆
            if(!TextUtils.isEmpty(qq_id)){
                userInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_USER_INFO_QQ);
                if(!TextUtils.isEmpty(userInfo)){
                    try {
                        JSONObject jsonObject = new JSONObject(userInfo);
                        mUserInfo = new ManBaUserInfo();
                        mUserInfo.setPhotoUrl(jsonObject.getString(Appconstant.LOGIN_QQ_PHOTO));
                        mUserInfo.setNickName(jsonObject.getString(Appconstant.LOGIN_QQ_NAME));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals(Appconstant.LOGIN_WEIXIN)){
            // 微信登陆,
            String weixin_id = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_WEIXIN_ID);
            // TODO 网络请求登陆
            if(!TextUtils.isEmpty(weixin_id)){
                userInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_USER_INFO_WEIXIN);
                if(!TextUtils.isEmpty(userInfo)){
                    try {
                        JSONObject jsonObject = new JSONObject(userInfo);
                        mUserInfo = new ManBaUserInfo();
                        mUserInfo.setPhotoUrl(jsonObject.getString(Appconstant.LOGIN_WEIXIN_PHOTO));
                        mUserInfo.setNickName(jsonObject.getString(Appconstant.LOGIN_WEIXIN_NAME));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }else{
            // 用户名密码登陆
            String phone = SharedPreferencesUtil.getInstance().getSP("phone");
            String password = SharedPreferencesUtil.getInstance().getSP("password");
            if ("18516145120".equals(phone) && "123456".equals(password)) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.bt_login)
    public void login() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivityForResult(intent, LOGIN_CODE);
    }


//    @Override
//    public void onClick(View view) {
//        Intent intent;
//        switch (view.getId()) {
//            case R.id.bt_login:
//                intent = new Intent();
//                intent.setClass(getActivity(), LoginActivity.class);
//                startActivityForResult(intent, LOGIN_CODE);
//                break;
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGIN_CODE:
                if (resultCode == Activity.RESULT_OK) {
//                    String type = data.getStringExtra(Appconstant.LOGIN_RETURN_TYPE);
//                    ManBaUserInfo mUserInfo = null;
//                    String nickname = "";
//                    String photoUrl = "";
//                    int sex = -1;
//                    mUserInfo = data.getParcelableExtra(Appconstant.LOGIN_USER_INFO);
//                    nickname = mUserInfo.getNickName();
//                    photoUrl = mUserInfo.getPhotoUrl();
                    String userID = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
                    String token = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.TOKEN);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add(Appconstant.User.TOKEN, token)
                            .add(Appconstant.User.USER_ID, userID)
                            .build();
                    Request request = new Request.Builder()
                            .url(ManBaApi.HTTP_GET_USER_INFO+userID)
                            .header("Accept","*/*")
                            .addHeader("Authorization",token)
                            //.post(body)
                            .build();
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseStr = response.body().string();
                            final UserInfoResponseModel userInfoResponseModel = new Gson().fromJson(responseStr,UserInfoResponseModel.class);
                            LogUtil.d(TAG, "-----response---------" + responseStr);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //initUserInfo(userInfoResponseModel.toManBaUserInfo());
                                    initUserInfo(userInfoResponseModel);
                                }
                            });

                        }
                    });

//                    user_name.setText(nickname);
//                    glideRequest.load(photoUrl).transform(new GlideCircleTransform(getActivity())).into(user_pic);
                } else {
                    Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
