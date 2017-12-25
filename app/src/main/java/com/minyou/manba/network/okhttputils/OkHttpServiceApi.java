package com.minyou.manba.network.okhttputils;

/**
 * Created by luchunhao on 2017/12/24.
 */
public class OkHttpServiceApi {

    // 网络请求链接
    public static String BASE_URL = "http://www.mymanba.cn/";

    // 获取工会列表
    public static String HTTP_GET_GUILD_LIST = "api/guild/list";

    // 发布动态
    public static String HTTP_ZONE_PUBLISH = BASE_URL + "api/zone/publish";
    // 修改头像
    public static String HTTP_USER_UPLOAD_PHOTO = BASE_URL + "api/user/uploadPhoto";

    // 根据ID查询用户信息
    public static String HTTP_GET_USER_BY_ID = "api/user/detail";

    // 根据ID获取动态详情
    public static String HTTP_GET_ZONE_DETAIL = "api/zone/detail";

    // 获取动态列表
    public static String HTTP_GET_ZONE_LIST = "api/zone/list";
}
