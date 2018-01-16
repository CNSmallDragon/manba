package com.minyou.manba.network.utils;

import com.minyou.manba.util.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by luchunhao on 2017/12/17.
 */
public class Common {


    /**
     * rx_call: RxCachedThreadScheduler-1    --io线程
     * rx_map: main                          --主线程
     * rx_subscribe: main                    --主线程
     */
    public static void rxJava() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        LogUtil.d("rx_call", Thread.currentThread().getName());

                        subscriber.onNext("dd");
                        subscriber.onCompleted();
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        LogUtil.d("rx_map", Thread.currentThread().getName());
                        return s + "88";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.d("rx_subscribe", Thread.currentThread().getName());
                    }
                });
    }


}
