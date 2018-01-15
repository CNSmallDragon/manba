package com.minyou.manba.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.GameCenterActivity;
import com.minyou.manba.activity.HomeActivity;
import com.minyou.manba.activity.LoginActivity;
import com.minyou.manba.activity.MyWalletActivity;
import com.minyou.manba.activity.PersonContentActivity;
import com.minyou.manba.activity.PersonInfoActivity;
import com.minyou.manba.activity.SettingActivity;
import com.minyou.manba.activity.ShouCangActivity;
import com.minyou.manba.activity.SociationDetailActivity;
import com.minyou.manba.databinding.FragmentMineBinding;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.imageloader.GlideImageLoader;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.QQResponseModel;
import com.minyou.manba.network.resultModel.UserLoginResultModel;
import com.minyou.manba.network.resultModel.WeiXinResponseModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;

public class MineFragment extends DataBindingBaseFragment implements View.OnClickListener {

    private static final String TAG = "MineFragment";
    public static final int LOGIN_CODE = 100;
    public static final int HEAD_PIC = 1001;
    public static final String REQUEST = "requestInfo";

    private FragmentMineBinding binding;

    private RequestManager glideRequest;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        glideRequest = Glide.with(getActivity());
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);

        initListener();
        return binding.getRoot();
    }



    private void initListener() {
        binding.mineGonghui.setOnClickListener(this);
        binding.mineQianbao.setOnClickListener(this);
        binding.mineShoucang.setOnClickListener(this);
        binding.mineDongtai.setOnClickListener(this);
        binding.mineXiaoxi.setOnClickListener(this);
        binding.mineHaiwan.setOnClickListener(this);
        binding.mineSetting.setOnClickListener(this);
        binding.rlAfterLogin.setOnClickListener(this);
        binding.ivUserPic.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume===");
        if (UserManager.isLogin()) {
            autoLogin();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_after_login:   //个人中心
                intent = new Intent(getActivity(), PersonInfoActivity.class);
                intent.putExtra("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                startActivity(intent);
                break;
            case R.id.iv_user_pic:      // 更换用户头像
                chooseHeadPid();
                break;
            case R.id.mine_gonghui:
                // 我的公会
                intent = new Intent(getActivity(), SociationDetailActivity.class);
                //intent.putExtra(Appconstant.SOCIATION_ID, String.valueOf(bean.getGuildId()));
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
                intent = new Intent(getActivity(), PersonContentActivity.class);
                intent.putExtra(Appconstant.PERSON_CENTER,Integer.parseInt(UserManager.getUserId()));
                startActivity(intent);
                break;
            case R.id.mine_xiaoxi:
                // 消息
                ((HomeActivity) getActivity()).getRg_main().check(R.id.rb_jiequ);
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
     * 选择头像
     */
    private void chooseHeadPid() {
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = 140;       // 圆形半径
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        // 图片保存狂傲
        imagePicker.setOutPutX(800);
        imagePicker.setOutPutY(800);

        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
        //ImagePicker.getInstance().setSelectedImages(images);
        startActivityForResult(intent, HEAD_PIC);
    }

    /**
     * 初始化个人信息
     *
     * @param object
     */
    private void initUserInfo(Object object) {
        if (object instanceof WeiXinResponseModel) {
            WeiXinResponseModel weiXinResponseModel = (WeiXinResponseModel) object;
            binding.tvUsername.setText(weiXinResponseModel.getNickname());
            if (!TextUtils.isEmpty(weiXinResponseModel.getHeadimgurl())) {
                glideRequest.load(weiXinResponseModel.getHeadimgurl()).transform(new GlideCircleTransform(getActivity())).into(binding.ivUserPic);
            }
        } else if (object instanceof UserLoginResultModel.ResultBean) {// 正常登陆返回
            UserLoginResultModel.ResultBean info = (UserLoginResultModel.ResultBean) object;
            LogUtil.d(TAG, info.getNickName());
            binding.tvUsername.setText(info.getNickName());
            LogUtil.d(TAG, info.getPhotoUrl());
            if (!TextUtils.isEmpty(info.getPhotoUrl())) {
                glideRequest.load(info.getPhotoUrl()).transform(new GlideCircleTransform(getActivity())).into(binding.ivUserPic);
            }
        } else if (object instanceof QQResponseModel) { // qq登陆返回
            QQResponseModel qqResponseModel = (QQResponseModel) object;
            LogUtil.d(TAG, "----" + qqResponseModel.getFigureurl_qq_2());
            binding.tvUsername.setText(qqResponseModel.getNickname());
            if (!TextUtils.isEmpty(qqResponseModel.getFigureurl_qq_2())) {
                glideRequest.load(qqResponseModel.getFigureurl_qq_2()).transform(new GlideCircleTransform(getActivity())).into(binding.ivUserPic);
            }
        }
//        binding.rlAfterLogin.setVisibility(View.VISIBLE);
//        binding.llAfterLogin.setVisibility(View.VISIBLE);
//        binding.rlBeforeLogin.setVisibility(View.GONE);
    }


    /**
     * 注册后返回数据
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void regstReturn(EventInfo info) {
        if (info.getType() == EventInfo.REGIST_RETURN) {
            //getUserInfo();
        }
    }

    /**
     * 个人资料修改信息后返回数据
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void editUserInfoReturn(EventInfo info) {
        if (info.getType() == EventInfo.SETTING_USER_INFO) {
            UserLoginResultModel.ResultBean bean = (UserLoginResultModel.ResultBean) info.getData();
            if (!TextUtils.isEmpty(bean.getNickName())) {
                binding.tvUsername.setText(bean.getNickName());
            }
            if (!TextUtils.isEmpty(bean.getPhotoUrl())) {
                glideRequest.load(bean.getPhotoUrl()).transform(new GlideCircleTransform(getActivity())).into(binding.ivUserPic);
            }
        }
    }

    /**
     * 自动登陆
     *
     * @return
     */
    private void autoLogin() {
        String userInfo = "";
        String lastLoginType = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_LAST_TYPE);
        LogUtil.d(TAG, "lastLoginType--------------" + lastLoginType);
        if (!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("2")) {
            // qq登陆,
            String qqInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_QQ_INFO);
            QQResponseModel qqModel = new Gson().fromJson(qqInfo, QQResponseModel.class);
            if (null != qqModel) {
                initUserInfo(qqModel);
            }
        } else if (!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("3")) {
            // 微信登陆,
            String wxInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_WX_INFO);
            if (TextUtils.isEmpty(wxInfo)) {
                wxInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.LOGIN_USER_INFO_WEIXIN);
            }
            WeiXinResponseModel weixinModel = new Gson().fromJson(wxInfo, WeiXinResponseModel.class);
            //EventBus.getDefault().post(new MessageEvent(resultStr));
            if (null != weixinModel) {
                initUserInfo(weixinModel);
            }
        } else if (!TextUtils.isEmpty(lastLoginType) && lastLoginType.equals("1")) {
            // 用户名密码登陆
            userInfo = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_INFO);
            UserLoginResultModel userLoginModelResult = new Gson().fromJson(userInfo, UserLoginResultModel.class);
            if (null != userLoginModelResult) {
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
            case HEAD_PIC:
                if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    // 只有一个
                    if (images.size() > 0) {  // 返回成功
                        String picUrl = images.get(0).path;
                        uploadHeadPhoto(picUrl);
                    }
                }
                break;
        }
    }

    /**
     * 上传用户头像
     *
     * @param picUrl
     */
    private void uploadHeadPhoto(final String picUrl) {
        LogUtil.d(TAG, "picUrl===" + picUrl);
        List<File> zoneFile = new ArrayList<File>();
        File file = new File(picUrl);
        if (!file.exists()) {
            return;
        }
        zoneFile.add(file);
        loading();
        String userID = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);
        HashMap<String, Object> params = new HashMap<>();
        params.put(Appconstant.User.USER_ID, userID);
        params.put("file", zoneFile);
        ManBaRequestManager.getInstance().upLoadFile(OkHttpServiceApi.HTTP_USER_UPLOAD_PHOTO + "/" + userID, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                glideRequest.load(picUrl).transform(new GlideCircleTransform(getActivity())).into(binding.ivUserPic);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                LogUtil.e(TAG, "errorMsg:" + errorMsg);
                cancelLoading();
            }
        });
    }


    public void getUserInfo(Intent data) {
        if (null == data) {
            return;
        }
        UserLoginResultModel.ResultBean userLoginModel = data.getParcelableExtra(Appconstant.LOGIN_USER_INFO);
        initUserInfo(userLoginModel);
    }


}
