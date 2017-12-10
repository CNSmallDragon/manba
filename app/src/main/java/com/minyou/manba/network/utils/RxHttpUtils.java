package com.minyou.manba.network.utils;


import com.minyou.manba.Appconstant;
import com.minyou.manba.network.RetrofitServiceGenerator;
import com.minyou.manba.network.RetryWhenNetworkException;
import com.minyou.manba.network.api.ManBaApiService;
import com.minyou.manba.network.resultModel.AuthTokenResultModel;
import com.minyou.manba.util.SPUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RxHttpUtils {

    // 创建网络请求(每次请求都先请求token 的创建方法)
    public static <T> Observable<T> createHttpRequestWithToken(final Observable<T> observable) {
        return RetrofitServiceGenerator.createTokenService(ManBaApiService.class)
                .http_getAssetsToken(Appconstant.NET_WORK_TOKEN_PARAMS.TOKEN_PAMAS_MAP)
                .flatMap(new Func1<AuthTokenResultModel, Observable<T>>() {
                    @Override
                    public Observable<T> call(AuthTokenResultModel authTokenResultModel) {
                        if (authTokenResultModel != null) {
                            SPUtils.put(Appconstant.AUTH_TOKEN_KEY, authTokenResultModel.getAccess_token());
                            return observable;
                        }
                        return null;
                    }
                })
                .retryWhen(new RetryWhenNetworkException()) // 重试
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 创建网络请求（不带token）
    public static <T> Observable<T> createHttpRequest(final Observable<T> observable) {
        return observable
                .retryWhen(new RetryWhenNetworkException()) // 重试
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 创建合并网络请求 (merge)
    public static <T> Observable<T> mergeHttpRequest(final Observable<T>... observables) {
        return Observable.merge(observables)
                .retryWhen(new RetryWhenNetworkException()) // 重试
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
