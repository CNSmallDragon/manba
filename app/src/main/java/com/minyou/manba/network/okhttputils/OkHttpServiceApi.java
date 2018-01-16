package com.minyou.manba.network.okhttputils;

/**
 * Created by luchunhao on 2017/12/24.
 */
public class OkHttpServiceApi {

    // 网络请求链接
    //public static String BASE_URL = "http://www.mymanba.cn/";
    public static String BASE_URL = "http://pre.mymanba.cn:8081/";

    // 登陆
    public static String HTTP_POST_LOGIN = "api/auth/login";

    // 重置密码
    public static String HTTP_POST_RESET_PWD = "api/user/updatePass";

    // 获取工会列表
    public static String HTTP_GET_GUILD_LIST = "api/guild/list";

    // 获取用户所在工会列表
    public static String HTTP_GET_USER_GUILD_LIST = "api/guild/userGuilds";

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

    // 获取收藏列表
    public static String HTTP_GET_ZONE_FAVORITELIST = "api/zone/favoriteList";

    // 根据ID查询用户信息
    public static String HTTP_GET_USER_DETAIL = "api/user/detail";

    // 修改用户信息
    public static String HTTP_POST_USER_UPDATE = "api/user/update";

    // 关注某人
    public static String HTTP_POST_ZONE_FOLLOW = "api/zone/follow";

    // 点赞某条动态
    public static String HTTP_POST_ZONE_UPVOTE = "api/zone/upvote";

    // 收藏动态
    public static String HTTP_POST_ZONE_FAVORITE = "api/zone/favorite";

    // 评论某条动态
    public static String HTTP_POST_ZONE_COMMENT = "api/zone/comment";

    // 点赞列表
    public static String HTTP_POST_ZONE_UPVOTELIST = "api/zone/upvoteList";

    // 查询评论列表（只查询父评论）
    public static String HTTP_GET_ZONE_COMMENTLIST = "api/zone/queryCommentList";

    // 查询评论回复列表
    public static String HTTP_GET_ZONE_REPLYCOMMENTLIST = "api/zone/queryReplyCommentList";

    // 评论点赞
    public static String HTTP_ZONE_COMMENTUPVOTE = "api/zone/commentUpvote";

    // 获取用户主页
    public static String HTTP_USER_MYHOME = "api/user/myHome";

    // 获取用户相册
    public static String HTTP_USER_GALLERY = "api/zone/photoList";

    // 获取公会详情
    public static String HTTP_GUILD_DETAIL = "api/guild/detail";

    // 获取公会成员
    public static String HTTP_GUILD_MEMBER = "api/guild/members";

    // 加入公会
    public static String HTTP_GUILD_JOIN = "api/guild/add";

    // 退出公会
    public static String HTTP_GUILD_EXIT = "api/guild/quit";
}
