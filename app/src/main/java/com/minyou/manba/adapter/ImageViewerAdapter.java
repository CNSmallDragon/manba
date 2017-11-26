package com.minyou.manba.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */
public class ImageViewerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> list;
    private RequestManager glideRequest;

    public ImageViewerAdapter(Context context,List<Integer> list){
        glideRequest = Glide.with(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        glideRequest.load(list.get(position)).into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.activity_alpha_out,0);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(((Activity)context), "position="+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
