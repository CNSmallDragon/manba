package com.minyou.manba.network.api;


import com.minyou.manba.network.responseModel.AuthTokenResultModel;
import com.minyou.manba.network.responseModel.UserLoginModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/8.
 */
public interface ManBaApiService {

    /**
     * 获取token
     *
     * @return 返回一个json数组
     */
    @FormUrlEncoded
    @POST("OAuth/Token")
    Observable<AuthTokenResultModel> http_getAssetsToken(@FieldMap Map<String, String> map);


    @POST("api/auth/login")
    Observable<UserLoginModel> http_login(@Body RequestBody requestBody);

    /**
     * 获取币种列表
     */
    @GET("api/Country/GetCurrencyList")
    Observable<String> http_getCurrencyList();

    /**
     * 修改用户信息
     */
    @POST("api/UserCenter/SettingUserInfo")
    Observable<String> http_settingUserInfo(@Body RequestBody requestBody);

}
