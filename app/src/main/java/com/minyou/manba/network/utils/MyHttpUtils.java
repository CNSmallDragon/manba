package com.minyou.manba.network.utils;

import com.minyou.manba.Appconstant;
import com.minyou.manba.network.httpInterface.HttpResultListener;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by luchunhao on 2017/12/14.
 */
public class MyHttpUtils {

    public static void http_getRequest(String url, final HttpResultListener mListener){
        String token = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.TOKEN);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","*/*")
                .addHeader("Authorization",token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mListener.onResponse(response);

            }
        });
    }

    public static void http_postRequest(String url, RequestBody requestBody, final HttpResultListener mListener){
        String token = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.TOKEN);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","*/*")
                .addHeader("Authorization",token)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mListener.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mListener.onResponse(response);

            }
        });
    }


}
