package com.minyou.manba.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.minyou.manba.R;
import com.minyou.manba.adapter.MyViewPagerAdapter;
import com.minyou.manba.pager.BasePager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
	
	private List<BasePager> tabs = new ArrayList<BasePager>();
	private List<BaseFragment> fragments;

	@BindView(R.id.tab_layou)
	TabLayout tab_layou;
	@BindView(R.id.pager)
	ViewPager pager;

	@BindString(R.string.home_xinxian)
	String xinxian;
	@BindString(R.string.home_remen)
	String remen;
	@BindString(R.string.home_guanzhu)
	String guanzhu;

//	private String[] titles = new String[]{xinxian,remen,guanzhu};
	private String[] titles;

	private void initData() {
//		tabs.clear();
//		tabs.add(new NewPager(getActivity(), getResources().getString(R.string.home_xinxian)));
//		tabs.add(new HotPager(getActivity(), getResources().getString(R.string.home_remen)));
//		tabs.add(new GuanZhuPager(getActivity(), getResources().getString(R.string.home_guanzhu)));
		//循环注入标签
		titles = new String[]{xinxian,remen,guanzhu};
		for(String tab:titles){
			tab_layou.addTab(tab_layou.newTab().setText(tab));
		}
	}

	@Override
	public int getContentViewId() {
		return R.layout.fragment_home;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		tab_layou.setTabMode(TabLayout.MODE_FIXED);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initData();

		tab_layou.setOnTabSelectedListener(this);
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new NewFragment());
		fragments.add(new HotFragment());
		fragments.add(new GuanZhuFragment());

		MyViewPagerAdapter adapter = new MyViewPagerAdapter(getFragmentManager(),titles,fragments);
		pager.setAdapter(adapter);
		tab_layou.setupWithViewPager(pager);
	}


	@Override
	public void onTabSelected(TabLayout.Tab tab) {
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(TabLayout.Tab tab) {

	}

	@Override
	public void onTabReselected(TabLayout.Tab tab) {

	}
}
