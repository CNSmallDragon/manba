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

    // 根据ID查询用户信息
    public static String HTTP_GET_USER_DETAIL = "api/user/detail";

    // 修改用户信息
    public static String HTTP_POST_USER_UPDATE = "api/user/update";

    // 关注某人
    public static String HTTP_POST_ZONE_FOLLOW = "api/zone/follow";

    // 点赞某条动态
    public static String HTTP_POST_ZONE_UPVOTE = "api/zone/upvote";

    // 点赞某条动态
    public static String HTTP_POST_ZONE_FAVORITE = "api/zone/favorite";

    // 评论某条动态
    public static String HTTP_POST_ZONE_COMMENT = "api/zone/comment";

    // 点赞列表
    public static String HTTP_POST_ZONE_UPVOTELIST = "api/zone/upvoteList";

    // 评论列表
    public static String HTTP_POST_ZONE_COMMENTLIST = "api/zone/queryCommentList";
}