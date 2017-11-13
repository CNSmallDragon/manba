package com.minyou.manba.pager;

import android.content.Context;
import android.view.View;

public abstract class BasePager {
	
	private static final String TAG = "BasePager";
	
	protected Context context;
	protected String title;
	protected View view;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BasePager(Context context,String title) {
		this.context = context;
		this.title = title;
		view = initView();
	}
	
	public View getRootView(){
		return view;
	}

	// 加载数据
	public void loadData(){
		// TODO 加载数据
	}
	
	public abstract View initView();
	public abstract void initData();
	
}
