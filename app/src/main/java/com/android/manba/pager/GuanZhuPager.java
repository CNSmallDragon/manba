package com.android.manba.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.manba.R;

public class GuanZhuPager extends BasePager {
	

	public GuanZhuPager(Context context,String title) {
		super(context, title);
	}

	@Override
	public View initView() {
		TextView view = new TextView(context);
		view.setText(context.getResources().getString(R.string.home_guanzhu));
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
