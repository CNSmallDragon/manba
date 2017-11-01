package com.android.manba.adapter;

import java.util.List;

import com.android.manba.R;
import com.android.manba.pager.BasePager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TabPagerAdapter extends PagerAdapter {
	
	private List<BasePager> list = null;
	private Context context;
	
	public TabPagerAdapter(Context context,List<BasePager> list) {
		this.list = list;
		this.context = context;
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
