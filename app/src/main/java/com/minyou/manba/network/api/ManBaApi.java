package com.minyou.manba.network.api;

/**
 * Created by luchunhao on 2017/12/13.
 */
public class ManBaApi {
    // 网络请求链接
    public static String BASE_URL = "http://www.mymanba.cn/";
    // 登陆请求
    public static String LOGINT_URL = "http://www.mymanba.cn/api/auth/login";

    // 获取用户信息
    public static String HTTP_GET_USER_INFO = "http://www.mymanba.cn/api/user/detail/";

    // 获取短信验证码
    public static String HTTP_GET_SEND_SMS = "http://www.mymanba.cn/api/user/sendSms";

    // 用户注册
    public static String HTTP_POST_REGIST = "http://www.mymanba.cn/api/user/register";

    // 用户注册
    public static String HTTP_POST_PUBLISH = BASE_URL + "api/zone/publish";

    // 获取动态列表
    public static String HTTP_GET_PUBLISH_LIST = BASE_URL + "api/zone/list";

    // 获取工会列表
    public static String HTTP_GET_GUILD_LIST = BASE_URL + "api/guild/list";

    // 发布动态
    public static String HTTP_POST_ZONE_PUBLISH = BASE_URL + "api/zone/publish";
}
