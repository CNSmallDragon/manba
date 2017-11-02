package com.android.manba.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.manba.R;
import com.android.manba.adapter.TabPagerAdapter;
import com.android.manba.pager.BasePager;
import com.android.manba.pager.GuanZhuPager;
import com.android.manba.pager.HotPager;
import com.android.manba.pager.NewPager;
import com.android.manba.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
	
	private List<BasePager> tabs = new ArrayList<BasePager>();
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home, null);
		// 初始化tab标题
		initData();
		TabPagerAdapter adapter = new TabPagerAdapter(tabs);

        ViewPager pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
		
		return view;
	}
	
	private void initData() {
		tabs.clear();
		tabs.add(new NewPager(getActivity(), getResources().getString(R.string.home_xinxian)));
		tabs.add(new HotPager(getActivity(), getResources().getString(R.string.home_remen)));
		tabs.add(new GuanZhuPager(getActivity(), getResources().getString(R.string.home_guanzhu)));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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
