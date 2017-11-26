package com.minyou.manba.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	
	private ScrollViewListener listener = null;

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOnScrollChangedListener(ScrollViewListener listener){
		this.listener = listener;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (listener != null) {
			listener.onScroll(this, l, t, oldl, oldt);
        }
	}

//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//		super.dispatchDraw(canvas);
//		if (listener != null) {
//			listener.onScroll(this, -1, getScrollY(), -1, -1);
//		}
//	}
}
