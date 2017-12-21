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
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.resultModel.QQResponseModel;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.network.resultModel.WeiXinResponseModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

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
        glideRequest = Glide.with(getActivity());
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LogUtil.d(TAG,"initView===");
        if (UserManager.isLogin()) {
            autoLogin();
        } else {
            rl_after_login.setVisibility(View.GONE);
            ll_after_login.setVisibility(View.GONE);
            rl_before_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(null);
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG,"isVisibleToUser===" + isVisibleToUser);
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
                getActivity().finish();
                break;
        }
    }

    /**
     * 初始化个人信息
     * @param object
     */
    private void initUserInfo(Object object){
        LogUtil.d(TAG,"----" + object.getClass());
        if(object instanceof WeiXinResponseModel){
            WeiXinResponseModel weiXinResponseModel = (WeiXinResponseModel) object;
            user_name.setText(weiXinResponseModel.getNickname());
            if(!TextUtils.isEmpty(weiXinResponseModel.getHeadimgurl())){
                glideRequest.load(weiXinResponseModel.getHeadimgurl()).transform(new GlideCircleTransform(getActivity())).into(user_pic);
            }
        }else if(object instanceof UserLoginResultModel.ResultBean){// 正常登陆返回
            UserLoginResultModel.ResultBean info = (UserLoginResultModel.ResultBean) object;
            LogUtil.d(TAG,info.getNickName());
            user_name.setText(info.getNickName());
            LogUtil.d(TAG,user_name.getText().toString());
            if(!TextUtils.isEmpty(info.getPhotoUrl())){
                glideRequest.load(info.getPhotoUrl()).transform(new GlideCircleTransform(getActivity())).into(user_pic);
            }
        }else if(object instanceof QQResponseModel){ // qq登陆返回
            QQResponseModel qqResponseModel = (QQResponseModel) object;
            LogUtil.d(TAG,"----" + qqResponseModel.getFigureurl_qq_2());
            user_name.setText(qqResponseModel.getNickname());
            if(!TextUtils.isEmpty(qqResponseModel.getFigureurl_qq_2())){
                glideRequest.load(qqResponseModel.getFigureurl_qq_2()).transform(new GlideCircleTransform(getActivity())).into(user_pic);
            }
        }
        rl_after_login.setVisibility(View.VISIBLE);
        ll_after_login.setVisibility(View.VISIBLE);
        rl_before_login.setVisibility(View.GONE);
    }

//    /**
//     * 登陆后返回数据
//     * @param messageEvent
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void loginReturn(MessageEvent messageEvent){
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(messageEvent.getMessage());
//            ManBaUserInfo mUserInfo = new ManBaUserInfo();
//            mUserInfo.setNickName(jsonObject.get(Appconstant.LOGIN_WEIXIN_NAME).toString().trim());
//            mUserInfo.setSex(Integer.parseInt(jsonObject.get(Appconstant.LOGIN_WEIXIN_SEX).toString().trim()));
//            mUserInfo.setPhotoUrl(jsonObject.get(Appconstant.LOGIN_WEIXIN_PHOTO).toString().trim());
//            initUserInfo(mUserInfo);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 注册后返回数据
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void regstReturn(EventInfo info){
        if(info.getType() == EventInfo.REGIST_RETURN){
            //getUserInfo();
        }
    }

    /**
     * 自动登陆
     * @return
     */
    private void autoLogin() {
        String userInfo = "";
        String lastLoginType = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE);
        LogUtil.d(TAG, "lastLoginType--------------" + lastLoginType);
        if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("2")){
            // qq登陆,
            String qqInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_QQ_INFO);
            QQResponseModel qqModel = new Gson().fromJson(qqInfo, QQResponseModel.class);
            if(null != qqModel){
                initUserInfo(qqModel);
            }
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("3")){
            // 微信登陆,
            String wxInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_WX_INFO);
            if(TextUtils.isEmpty(wxInfo)){
                wxInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_USER_INFO_WEIXIN);
            }
            WeiXinResponseModel weixinModel = new Gson().fromJson(wxInfo,WeiXinResponseModel.class);
            //EventBus.getDefault().post(new MessageEvent(resultStr));
            if(null != weixinModel){
                initUserInfo(weixinModel);
            }
        }else if(!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("1")){
            // 用户名密码登陆
            userInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_INFO);
            UserLoginResultModel userLoginModelResult = new Gson().fromJson(userInfo,UserLoginResultModel.class);
            if(null != userLoginModelResult){
                initUserInfo(userLoginModelResult.getResult());
            }
        }
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
                    //getUserInfo();
                    getUserInfo(data);
                } else {
                    //Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void getUserInfo(Intent data) {
        if(null == data){
            return;
        }
        UserLoginResultModel.ResultBean userLoginModel = data.getParcelableExtra(Appconstant.LOGIN_USER_INFO);
        initUserInfo(userLoginModel);
    }
}
