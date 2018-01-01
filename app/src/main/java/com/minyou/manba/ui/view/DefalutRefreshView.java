package com.minyou.manba.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.minyou.manba.R;
import com.minyou.manba.util.LogUtil;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by luchunhao on 2017/12/28.
 */
public class DefalutRefreshView extends FrameLayout implements PtrUIHandler {

    private static final String TAG = "DefalutRefreshView";

    public DefalutRefreshView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DefalutRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DefalutRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DefalutRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        this.setLayoutParams(new PtrFrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
//        setBackgroundColor(Color.CYAN);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.refresh_headtview_layout, null);
        addView(view);
    }
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        LogUtil.d(TAG, "--- onUIReset ---");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        LogUtil.d(TAG, "--- onUIRefreshPrepare ---");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        LogUtil.d(TAG, "--- onUIRefreshBegin ---");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        LogUtil.d(TAG, "--- onUIRefreshComplete ---");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        LogUtil.d(TAG, "--- onUIPositionChange ---");
    }
}
