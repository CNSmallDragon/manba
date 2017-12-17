package com.minyou.manba.network;


import com.minyou.manba.MyApplication;
import com.minyou.manba.R;
import com.minyou.manba.commonInterface.OnHttpResultListener;
import com.minyou.manba.network.responseModel.BaseResponseModel;

import rx.Subscriber;


public class CallSubscriber<T>  extends Subscriber<T> {

    private OnHttpResultListener<T> mListener;// 回调

    public CallSubscriber(OnHttpResultListener<T> mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        _onStart();
        if (mListener != null) {
            mListener.onRequestStart();
        }
    }

    @Override
    public void onCompleted() {
        _onCompleted();
        if (mListener != null) {
            mListener.onRequestCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        _onError(MyApplication.getInstance().getResources().getString(R.string.sorry_network_is_not_good));
        if (mListener != null) {
            mListener.onRequestError(MyApplication.getInstance().getResources().getString(R.string.sorry_network_is_not_good));
        }
    }

    @Override
    public void onNext(T t) {
        if (t != null) {
            if (t instanceof BaseResponseModel) {
                BaseResponseModel resultModel = (BaseResponseModel) t;
                mListener.onRequestSuccess(t);
//                if (resultModel.isSuccess()) {
//                    _onNext(t);
//                    if (mListener != null) {
//                        mListener.onRequestSuccess(t);
//                    }
//                } else {
//                    if (mListener != null) {
//                        mListener.onRequestError(resultModel.getMessage());
//                    }
//                }
            }
        } else {
            if (mListener != null) {
                mListener.onRequestError(MyApplication.getInstance().getResources().getString(R.string.sorry_network_is_not_good));
            }
        }
    }

    public void _onStart(){};

    public void _onNext(T t){};

    public void _onError(String msg){};

    public void _onCompleted(){};
}

