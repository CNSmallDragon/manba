package com.minyou.manba.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minyou.manba.util.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
	
	private static final String TAG = "BaseFragment";
	protected Unbinder unbinder;
	public View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "oncreate---------");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "onCreateView---------");
		view = inflater.inflate(getContentViewId(),container,false);
		unbinder = ButterKnife.bind(this,view);
		initView(savedInstanceState);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "onActivityCreated---------");
		initData(savedInstanceState);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		if(null != getView()){
			getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
		}
		super.setMenuVisibility(menuVisible);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	/**
	 * 返回当前Fragment布局ID
	 * @return
     */
	public abstract int getContentViewId();

	/**
	 * 处理当前fragment布局view显示
	 * @param savedInstanceState
     */
	public abstract void initView(Bundle savedInstanceState);

	/**
	 * 处理当前fragment数据
	 * @param savedInstanceState
     */
	public abstract void initData(Bundle savedInstanceState);

}
