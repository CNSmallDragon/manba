package com.minyou.manba.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class GuanZhuPager extends BasePager {
	

	public GuanZhuPager(Context context,String title) {
		super(context, title);
	}

	@Override
	public View initView() {
		TextView view = new TextView(context);
		view.setText(title);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
