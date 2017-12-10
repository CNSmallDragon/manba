package com.minyou.manba.network.utils;

import com.google.gson.Gson;
import com.minyou.manba.util.Assistant;
import com.minyou.manba.util.LogUtil;

import okhttp3.RequestBody;

/**
 * Author:  fanlei
 * Time: 2017/9/8 18:11
 */

public class RequestBodyUtils {

    /**
     * 得到请求参数 对请求的参数进行加密
     *
     * @return
     */
    public static RequestBody getRequestBodyWithAssistant(Object obj) {
        String reqStr;
        reqStr = new Gson().toJson(obj);
        LogUtil.d("Okhttp", " 请求参数 ：" + reqStr);// 打印数据
        reqStr = Assistant.Boxing(reqStr);// 加密
        return RequestBody.create(okhttp3.MediaType.parse("application/text; charset=utf-8"), reqStr);
    }
    // MD5加密的传输
    public static RequestBody getRequestBody(Object obj) {
        String reqStr;
        reqStr = new Gson().toJson(obj);
        LogUtil.d("Okhttp", " 请求参数 ：" + reqStr);// 打印数据
        //reqStr = Assistant.BoxingWithMD5(reqStr);// 加密
        return RequestBody.create(okhttp3.MediaType.parse("application/text; charset=utf-8"), reqStr);
    }


}
