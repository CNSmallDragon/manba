package com.minyou.manba.network;


import com.minyou.manba.BuildConfig;
import com.minyou.manba.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Cteated by fanlei 2017/9/8
 * Retrofit 接口构建器
 */
public class RetrofitServiceGenerator {
    // token地址
    private static final String AUTH_TOKEN_URL = "http://192.168.4.114:8080/";


    // 接口地址(Base)
    private static final String DEBUG_BASE_URL = "http://www.mymanba.cn/";
    private static final String PROD_BASE_URL = "http://www.mymanba.cn/";
    public static final String URL_GET_PRODUCTIDS = "https://label.reportide.com/api/Label/ProductList?siteId=11&sourceType=3&tag=label-home&size=8";

    // 接口地址(Base)
    //private static final String SERVICE_URL = BuildConfig.DEBUG ? DEBUG_BASE_URL : PROD_BASE_URL;
    private static final String SERVICE_URL = BuildConfig.DEBUG ? DEBUG_BASE_URL : PROD_BASE_URL;

    //创建普通请求的Client
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//            .cache(new Cache(new File(TlzTbDressApplication.getInstance().getCacheDir(), "responses"), 30 * 1024 * 1024)) // 设置缓存的路径和大小(30M)
            .connectTimeout(10, TimeUnit.SECONDS)// 请求超时时间设置为 10 秒
            .readTimeout(10, TimeUnit.SECONDS)// 读取超时时间  10 秒
            .writeTimeout(10, TimeUnit.SECONDS)
//            .addNetworkInterceptor(new CatchHttpInterceptor()) // 缓存拦截器
            .addInterceptor(new AddHeadHttpInterceptor()) // 添加请求头
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));//加入过滤器，仅仅是为了打印请求头和返回的数据

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(SERVICE_URL)
                    .addConverterFactory(AESGsonConverterFactory.create())// 自定义转换器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()));


    private static AESGsonConverterFactory cusTomFactory = AESGsonConverterFactory.create();

    private static Retrofit.Builder customBuilder =
            new Retrofit.Builder()
                    .baseUrl(SERVICE_URL)
                    .addConverterFactory(cusTomFactory)// 自定义转换器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()));

    // 创建接口
    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    // 创建接口
    public static <T> T createService(Class<T> serviceClass, AESGsonResponseBodyConverter.JsonBridge jsonBridge) {
        cusTomFactory.setJsonBridge(jsonBridge);
        Retrofit retrofit = customBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


    //创建请求token的接口
    public static <T> T createTokenService(Class<T> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File(MyApplication.getInstance().getCacheDir(), "responses"), 30 * 1024 * 1024)) // 设置缓存的路径和大小
                .connectTimeout(15, TimeUnit.SECONDS)// 请求超时时间设置为 15 秒
                .readTimeout(15, TimeUnit.SECONDS)// 读取超时时间  15 秒
                .writeTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(new CatchHttpInterceptor()) // 缓存拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));//加入过滤器，仅仅是为了打印请求头和返回的数据

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(AUTH_TOKEN_URL)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .addConverterFactory(GsonConverterFactory.create());// 使用Gson的转换器
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


}