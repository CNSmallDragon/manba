package com.minyou.manba.pager;

import com.minyou.manba.R;

import android.content.Context;
import android.view.View;

public class NewPager extends BasePager {
	

	public NewPager(Context context,String title) {
		super(context, title);
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.item_content, null);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
