package com.minyou.manba.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minyou.manba.R;

public class ActionTitleView extends LinearLayout implements OnClickListener {

	private static final String TAG = "ActionTitleView";
	private LinearLayout view;
	private ImageView iv_back;
	private Context context;
	private TextView tv_title;
	private ImageView iv_title_right;

	public ActionTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public ActionTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		view = (LinearLayout) LinearLayout.inflate(context, R.layout.view_action_title, this);
		init();
	}

	public ActionTitleView(Context context) {
		super(context);
		this.context = context;
	}

	private void init() {
		iv_back = (ImageView) view.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
	}

	/**
	 * 隐藏返回按钮
	 */
	public void hideBackIcon(){
		iv_back.setVisibility(View.GONE);
	}

	// 设置标题
	public void setTitle(String title) {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		if (!TextUtils.isEmpty(title)) {
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(title);
		} else {
			tv_title.setVisibility(View.GONE);
		}
	}

	public void setTitleSize(int size){
		if(null != tv_title){
			tv_title.setTextSize(size);
		}
	}

	/**
	 * 设置右侧按钮显示及事件
	 *
	 * @param id
	 *            传入右侧按钮要显示的resource id
	 * @param theme
	 *            按钮样式主题
	 * @param listener
	 *            按钮点击事件
	 *
	 */
	@SuppressLint("NewApi")
	public void setRightToDo(int id, Theme theme, OnClickListener listener) {
		iv_title_right = (ImageView) view.findViewById(R.id.iv_title_right);
		if (id == 0) {
			iv_title_right.setVisibility(View.GONE);
		} else {
			iv_title_right.setVisibility(View.VISIBLE);
			iv_title_right.setBackground(context.getResources().getDrawable(id));
			iv_title_right.setOnClickListener(listener);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			Log.e(TAG, "aaaaaaaaaaaaaa");
			((Activity) getContext()).finish();
			break;

		default:
			break;
		}

	}

}
