package com.minyou.manba.network.okhttputils;

/**
 * Created by luchunhao on 2017/12/23.
 */
public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}
