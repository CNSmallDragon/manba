package com.android.manba.pager;

import com.android.manba.R;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotPager extends BasePager {

	
	public HotPager(Context context,String title) {
		super(context, title);
	}

	@Override
	public View initView() {
		TextView view = new TextView(context);
		view.setText("����");
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
