package com.minyou.manba.network.resultModel;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by luchunhao on 2017/12/13.
 */
public class BaseResultModel extends BaseObservable implements Serializable {

    protected String code;
    protected String msg;
    protected Object detail;
    protected boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
