package com.minyou.manba.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minyou.manba.R;
import com.minyou.manba.adapter.MyViewPagerAdapter;
import com.minyou.manba.databinding.FragmentHomeBinding;
import com.minyou.manba.pager.BasePager;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends DataBindingBaseFragment implements TabLayout.OnTabSelectedListener {

	private static final String TAG = "HomeFragment";
	
	private List<BasePager> tabs = new ArrayList<BasePager>();
	private List<DataBindingBaseFragment> fragments;

	private FragmentHomeBinding binding;

//	private String[] titles = new String[]{xinxian,remen,guanzhu};
	private String[] titles;

	private void initData() {
//		tabs.clear();
//		tabs.add(new NewPager(getActivity(), getResources().getString(R.string.home_xinxian)));
//		tabs.add(new HotPager(getActivity(), getResources().getString(R.string.home_remen)));
//		tabs.add(new GuanZhuPager(getActivity(), getResources().getString(R.string.home_guanzhu)));
		//循环注入标签
		titles = new String[]{getResources().getString(R.string.home_xinxian),getResources().getString(R.string.home_remen),getResources().getString(R.string.home_guanzhu)};
		for(String tab:titles){
			binding.tabLayou.addTab(binding.tabLayou.newTab().setText(tab));
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
		initView();
		return binding.getRoot();
	}



	public void initView() {
		binding.tabLayou.setTabMode(TabLayout.MODE_FIXED);
		initData();

		binding.tabLayou.setOnTabSelectedListener(this);
		fragments = new ArrayList<DataBindingBaseFragment>();
		fragments.add(new NewFragment());
		fragments.add(new NewFragment());
		fragments.add(new NewFragment());

		MyViewPagerAdapter adapter = new MyViewPagerAdapter(getFragmentManager(),titles,fragments);
		binding.pager.setAdapter(adapter);
		binding.tabLayou.setupWithViewPager(binding.pager);
	}


	@Override
	public void onTabSelected(TabLayout.Tab tab) {
		binding.pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(TabLayout.Tab tab) {

	}

	@Override
	public void onTabReselected(TabLayout.Tab tab) {

	}

}
