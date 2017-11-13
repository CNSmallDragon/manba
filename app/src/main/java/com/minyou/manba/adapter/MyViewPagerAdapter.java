package com.minyou.manba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minyou.manba.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> fragments;
    private String[] list = null;

    public MyViewPagerAdapter(FragmentManager fm,String[] list,List<BaseFragment> fragments) {
        super(fm);
        this.list = list;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list[position];
    }
}
