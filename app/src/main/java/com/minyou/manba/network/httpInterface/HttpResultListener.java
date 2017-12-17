package com.minyou.manba.network.httpInterface;

import okhttp3.Response;

/**
 * Created by luchunhao on 2017/12/14.
 */
public interface HttpResultListener {

    void onFailure();
    void onResponse(Response response);
}
