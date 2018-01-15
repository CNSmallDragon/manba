package com.minyou.manba.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.minyou.manba.R;
import com.minyou.manba.activity.ImageViewerActivity;
import com.minyou.manba.model.CustomImageModelLoader;
import com.minyou.manba.model.CustomImageSizeModel;
import com.minyou.manba.model.CustomImageSizeModelImp;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/11/26.
 */
public class ImageViewerAdapter extends PagerAdapter {

    private static final String TAG = "ImageViewerAdapter";

    private Context context;
    private List<CustomImageSizeModelImp> mDatas = new ArrayList<CustomImageSizeModelImp>();
    private OnItemLongClickListener onItemLongClick;

    public ImageViewerAdapter(Context context,List<String> list){
        this.context = context;
        for(String imageUrl : list){
            mDatas.add(new CustomImageSizeModelImp(imageUrl));
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager_image,container,false);
        PhotoView imageView = (PhotoView) view.findViewById(R.id.image);
        View loading = view.findViewById(R.id.progressBar);
        LogUtil.d(TAG,"instantiateItem===============" + position);
        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                LogUtil.d(TAG,"onPhotoTap===============");
                ((ImageViewerActivity)context).finish();
                ((ImageViewerActivity)context).overridePendingTransition(0,R.anim.activity_alpha_out);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LogUtil.d(TAG,"onLongClick===============");
                if(null != onItemLongClick){
                    onItemLongClick.setOnItemLongClickListener(view,position);
                }
                return true;
            }
        });
        container.addView(view);
        //Glide.with(context).load(mDatas.get(position).getBaseUrl()).into(imageView);
        displayImage(mDatas.get(position), imageView, loading);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public interface OnItemLongClickListener{
        void setOnItemLongClickListener(View view,int position);
    }

    public void setOnItemLongClick(OnItemLongClickListener onItemLongClick){
        this.onItemLongClick = onItemLongClick;
    }

    void displayImage(final CustomImageSizeModel model, final PhotoView imageView, final View loading) {
        DrawableRequestBuilder thumbnailBuilder = Glide
                .with(imageView.getContext())
                .load(new CustomImageSizeModelImp(model
                        .getBaseUrl())
                        .requestCustomSizeUrl(100, 50))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .using(new CustomImageModelLoader(imageView.getContext()))
                .load(model)
//                .load(model.getBaseUrl())
//                .centerCrop()
                .listener(new RequestListener<CustomImageSizeModel, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, CustomImageSizeModel model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, CustomImageSizeModel model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        loading.setVisibility(View.GONE);
                        PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
//                            mAttacher.update();
                        return false;
                    }
                })
                .thumbnail(thumbnailBuilder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
}
