package com.minyou.manba.network.api;

/**
 * Created by luchunhao on 2017/12/13.
 */
public class ManBaApi {
    // 网络请求链接
    // 登陆请求
    public static String LOGINT_URL = "http://www.mymanba.cn/api/auth/login";

    // 获取用户信息
    public static String HTTP_GET_USER_INFO = "http://www.mymanba.cn/api/user/detail/";

    // 获取短信验证码
    public static String HTTP_GET_SEND_SMS = "http://www.mymanba.cn/api/user/sendSms";
}
