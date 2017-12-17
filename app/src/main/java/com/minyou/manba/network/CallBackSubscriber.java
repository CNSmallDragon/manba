package com.minyou.manba.network;

import com.minyou.manba.MyApplication;
import com.minyou.manba.R;
import com.minyou.manba.commonInterface.OnHttpResultListener;
import com.minyou.manba.network.responseModel.BaseResponseModel;

import rx.Subscriber;

/**
 * Author:  fanlei
 * Time: 2017/9/12 15:22
 * <p>
 * 重写回调，放置公共操作
 */

public abstract class CallBackSubscriber<T> extends Subscriber<T> {

    private OnHttpResultListener<T> mListener;// 回调

    public CallBackSubscriber(OnHttpResultListener<T> mListener) {
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
        e.printStackTrace();
        //LogUtil.e("CallBackSubscriber",Log);
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

    public abstract void _onStart();

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);

    public abstract void _onCompleted();
}
