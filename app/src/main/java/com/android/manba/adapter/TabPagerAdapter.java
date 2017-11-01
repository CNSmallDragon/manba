package com.android.manba.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.manba.pager.BasePager;

import java.util.List;

public class TabPagerAdapter extends PagerAdapter {
	
	private List<BasePager> list = null;

	public TabPagerAdapter(List<BasePager> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		//View view = LinearLayout.inflate(context, R.layout.item_content, null);
//		TextView view = new TextView(context);
//		view.setText(list.get(position));
//		view.setTextColor(Color.BLACK);
		container.addView(list.get(position).getRootView());
		return list.get(position).getRootView();
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

}
