package com.android.manba.fragment;

import com.android.manba.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MineFragment extends BaseFragment {
	
	public MineFragment(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_mine, null);
		return view;
	}

	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

}
