package com.minyou.manba.model;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.commonInterface.OnHttpResultListener;
import com.minyou.manba.network.CallBackSubscriber;
import com.minyou.manba.network.RetrofitServiceGenerator;
import com.minyou.manba.network.api.ManBaApiService;
import com.minyou.manba.network.requestModel.UserLoginRequestModel;
import com.minyou.manba.network.responseModel.UserLoginModel;
import com.minyou.manba.network.utils.RequestBodyUtils;
import com.minyou.manba.network.utils.RxHttpUtils;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SPUtils;

/**
 * Created by luchunhao on 2017/12/10.
 */
public class LoginActivityModel {
    private static final String TAG = "MineModel";

    // 正常开始登陆
    public void startLogin(String phone, String pwd, final OnHttpResultListener<UserLoginModel> mListener) {
        // 构建请求参数
        UserLoginRequestModel requestModel = new UserLoginRequestModel();
        //requestModel.setLoginType(Appconstant.Config.LOGIN_YPTE_NORMAL);
        requestModel.setUserName(phone);
        requestModel.setPassword(pwd);
        // 开始网络请求
        RxHttpUtils.createHttpRequest(RetrofitServiceGenerator.createService(ManBaApiService.class)
                .http_login(RequestBodyUtils.getRequestBody(requestModel)))
                .subscribe(new CallBackSubscriber<UserLoginModel>(mListener) {
                    @Override
                    public void _onStart() {
                        LogUtil.d(TAG, "_onStart----------");
                    }

                    @Override
                    public void _onNext(UserLoginModel userInfoResultModel) {
                        LogUtil.d(TAG, "_onNext----------");
                        SPUtils.put(Appconstant.USER_INFO_KEY, new Gson().toJson(userInfoResultModel));

                    }

                    @Override
                    public void _onError(String msg) {
                        LogUtil.d(TAG, "_onError----------");
                    }

                    @Override
                    public void _onCompleted() {
                        LogUtil.d(TAG, "_onCompleted----------");
                    }
                });
    }
}
