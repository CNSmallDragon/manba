package com.android.manba.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.manba.R;

/**
 * Created by Administrator on 2017/11/1.
 */
public class MineItemView extends RelativeLayout {

    private RelativeLayout view;

    public MineItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = (RelativeLayout) View.inflate(context, R.layout.item_mine,this);
    }

    public MineItemView(Context context) {
        super(context);
    }
}
