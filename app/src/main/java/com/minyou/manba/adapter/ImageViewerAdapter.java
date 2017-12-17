package com.minyou.manba.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.activity.ImageViewerActivity;
import com.minyou.manba.util.LogUtil;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager_image,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        glideRequest.load(list.get(position)).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.d("lch---",context.getClass()+"");
                ((ImageViewerActivity)context).finish();
                ((ImageViewerActivity)context).overridePendingTransition(0,R.anim.activity_alpha_out);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(((Activity)context), "position="+position, Toast.LENGTH_SHORT).show();
                ((ImageViewerActivity)context).showPupWindowMenu(view);
                return true;
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
