package com.minyou.manba.network.utils;

import com.minyou.manba.Appconstant;
import com.minyou.manba.network.api.ManBaApi;
import com.minyou.manba.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by luchunhao on 2017/12/17.
 */
public class Common {

    public static void http_post(){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(Appconstant.Config.LOGIN_YPTE,Appconstant.Config.LOGIN_YPTE_NORMAL)
                .add("phone", "1234567890")
                .add("password", "123456")
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
                LogUtil.d("tag", "-----response---------" + requestStr);

            }
        });
    }


}
