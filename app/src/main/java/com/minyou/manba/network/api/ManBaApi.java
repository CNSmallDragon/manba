package com.minyou.manba.network.api;

/**
 * Created by luchunhao on 2017/12/13.
 */
public class ManBaApi {
    // 网络请求链接
    //public static String BASE_URL = "http://www.mymanba.cn/";
    public static String BASE_URL = "http://pre.mymanba.cn:8081/";
    // 登陆请求
    public static String LOGINT_URL = BASE_URL + "api/auth/login";

    // 获取短信验证码
    public static String HTTP_GET_SEND_SMS = BASE_URL + "api/user/sendSms";

    // 用户注册
    public static String HTTP_POST_REGIST = BASE_URL + "api/user/register";

}
