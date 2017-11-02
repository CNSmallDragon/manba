package com.android.manba.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.manba.util.LogUtil;

public abstract class BaseFragment extends Fragment {
	
	private static final String TAG = "BaseFragment";
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
		view = initView(inflater);
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

	public abstract View initView(LayoutInflater inflater);
	public abstract void initData(Bundle savedInstanceState);

}
