package com.minyou.manba.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
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
	private View viewLine;
	private Context context;
	private TextView tv_title;
	private TextView tv_title_right;
	private String title;
	private boolean isShowBorderLine = false;
	private boolean isShowBackIcon = true;

	public ActionTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ActionTitle);
		title = typedArray.getString(R.styleable.ActionTitle_titleText);
		isShowBorderLine = typedArray.getBoolean(R.styleable.ActionTitle_borderLine,false);
		isShowBackIcon = typedArray.getBoolean(R.styleable.ActionTitle_backIcon,true);
		typedArray.recycle();
		this.context = context;
		view = (LinearLayout) LinearLayout.inflate(context, R.layout.view_action_title, this);
		init();
	}

	public ActionTitleView(Context context, AttributeSet attrs) {
		this(context, attrs,0);

	}

	public ActionTitleView(Context context) {
		this(context,null);
	}

	private void init() {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		viewLine = view.findViewById(R.id.titlebar_line);
		iv_back = (ImageView) view.findViewById(R.id.iv_back);
		// 是否显示下划线
		if(isShowBorderLine){
			viewLine.setVisibility(View.VISIBLE);
		}else{
			viewLine.setVisibility(View.GONE);
		}
		// 设置标题
		if(TextUtils.isEmpty(title)){
			tv_title.setVisibility(View.GONE);
		}else{
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(title);
		}
		// 是否显示返回按钮
		if(isShowBackIcon){
			iv_back.setOnClickListener(this);
		}else{
			iv_back.setVisibility(View.GONE);
		}

	}

	/**
	 * 隐藏返回按钮
	 */
	public void hideBackIcon(){
		iv_back.setVisibility(View.GONE);
	}

	public void showBackIcon(){
		iv_back.setVisibility(View.VISIBLE);
	}

	// 设置标题
	public void setTitle(String title) {

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
	 * @param what
	 *            传入右侧按钮要显示的文字
	 * @param listener
	 *            按钮点击事件
	 *
	 */
	@SuppressLint("NewApi")
	public void setRightToDo(String what, OnClickListener listener) {
		tv_title_right = (TextView) view.findViewById(R.id.tv_title_right);
		if(!TextUtils.isEmpty(what)){
			tv_title_right.setVisibility(View.VISIBLE);
			tv_title_right.setText(what);
			tv_title_right.setOnClickListener(listener);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			((Activity) getContext()).finish();
			break;

		default:
			break;
		}

	}

}
