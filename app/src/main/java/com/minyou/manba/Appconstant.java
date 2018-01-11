package com.minyou.manba;

import java.util.HashMap;

public class Appconstant {

	public static String SP_FILE_NAME = "manba";
	public static final String AUTH_TOKEN_KEY = "auto_token_key"; // auth token的key
	public static final String USER_INFO_KEY = "user_info_key";   // 用户信息的key


	public static String QQ_APP_ID = "1106417254";
	public static String WEIXIN_APP_ID = "wxa5ec57998b013d7e";
	public static String WEIXIN_APP_SECRET = "c643c03297a37a86e3b1e1bcdf98609c";
	public static String SOCIATION_ID = "guild_id";


	// 登陆返回Intent标识
	public static String LOGIN_QQ = "qq";
	public static String LOGIN_QQ_ID = "qq_openid";
	public static String LOGIN_THIRD_ID = "openid";
	public static String LOGIN_WEIXIN = "weixin";
	public static String LOGIN_WEIXIN_ID = "weixin_openid";
	public static String LOGIN_QQ_PHOTO = "figureurl_qq_2";
	public static String LOGIN_QQ_NAME = "nickname";
	public static String LOGIN_WEIXIN_NAME = "nickname";
	public static String LOGIN_WEIXIN_SEX = "sex";
	public static String LOGIN_WEIXIN_PHOTO = "headimgurl";
	public static String LOGIN_RETURN_TYPE = "type";
	public static String LOGIN_USER_INFO = "user_info";
	public static String LOGIN_USER_INFO_WEIXIN = "user_info_weixin";
	public static String LOGIN_USER_INFO_QQ = "user_info_qq";
	// 记录最后一次登录方式
	public static String LOGIN_LAST_TYPE = "last_type";		// 1用户密码、2qq、3微信
	public static String LOGIN_OR_NOT = "isLogin";

	// 首页进入动态详情页面表示
	public static String ITEM_INFO = "item_info";

	// 个人中心进入公会
	public static String FROM_WHERE = "from_where";
	public static String MINE2GONGHUI = "mine2gonghui";

	// 进入个人主页
	public static String PERSON_CENTER = "person_center";

	// token所需参数
	public static final class NET_WORK_TOKEN_PARAMS {
		// 请求token的参数
		public static HashMap<String, String> TOKEN_PAMAS_MAP = new HashMap<String, String>();

		static {
			TOKEN_PAMAS_MAP.put("client_id", "276055911742");
			TOKEN_PAMAS_MAP.put("client_secret", "326A62D3749A48878748E02F9F8DC637");
			TOKEN_PAMAS_MAP.put("grant_type", "client_credentials");
			TOKEN_PAMAS_MAP.put("response_type", "token");
		}
	}

	// 网络所需要的参数
	public static final class NET_WORK_PAMAS {
		public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChLkDpMdtPigPOI3QI6vjlgkBSl3Q7wjLb3POBliVM0olJ2HtPaut+6pK76nvva4w4UYMj/hE+7VJDcwJdePDu6nTmxn6iSsUq3J6rDHLTmehiZnffDTWvpGvv9boqv7ItCf3mVw7EY4E05wfo2/ZcVuVxc7ZHIMP7ko0HVddvFwIDAQAB";// 加密的公钥
	}

	// 用户相关
	public static final class User {
		public static final String USER_ID = "userId";
		public static final String TOKEN = "token";
		public static final String TOKEN_REFRESH = "refreshToken";
		public static final String USER_THRID_INFO = "user_thrid_info";
		public static final String USER_QQ_INFO = "user_qq_info";
		public static final String USER_WX_INFO = "user_wx_info";
		public static final String USER_INFO = "user_info";
		// 正常登陆用户信息
		public static final String USER_PHONE = "phone" ;
		public static final String USER_PWD = "password" ;
	}

	public static final class Config{
		public static final String KEY = "myservice2017GHJKLATYIQ";
		public static final String LOGIN_YPTE = "loginType";
		public static final String LOGIN_YPTE_NORMAL = "1";	// 正常登陆
		public static final String LOGIN_YPTE_SMS = "2";	// 短信登陆
		public static final String LOGIN_YPTE_ThIRD = "3";	// 三方登陆
	}

}
