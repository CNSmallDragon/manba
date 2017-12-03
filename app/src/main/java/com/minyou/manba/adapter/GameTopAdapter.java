package com.minyou.manba.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.util.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 */
public class GameTopAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> list;
    private RequestManager requestManager;

    public GameTopAdapter(Context context,List<Integer> list){
        this.context = context;
        this.list = list;
        requestManager = Glide.with(context);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position%list.size();
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        requestManager.load(list.get(newPosition)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
