package com.minyou.manba.network;


import android.text.TextUtils;

import com.minyou.manba.Appconstant;
import com.minyou.manba.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:  fanlei
 * Time: 2017/9/9 10:56
 * <p>
 * 添加请求头的拦截器
 */

public class AddHeadHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        String auth_token = (String) SPUtils.get(Appconstant.User.TOKEN, " ");// token

        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                //.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                //.addHeader("Connection", "keep-alive")
                .addHeader("Authorization", TextUtils.isEmpty(auth_token)? "" : auth_token)
                .addHeader("Accept", "*/*")
                .method(originalRequest.method(), originalRequest.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
