package com.minyou.manba.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ItemGalleryLayoutBinding;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> list;
    private OnItemClickLitener mLitener;

    public GalleryAdapter(Context context,List<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGalleryLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_gallery_layout,parent,false);
        ImageViewHolder viewHolder = new ImageViewHolder(binding.getRoot());
        viewHolder.binding = binding;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ImageViewHolder){
            LogUtil.d("lch---",list.get(position));
            final ImageViewHolder viewHolder = (ImageViewHolder) holder;
            Glide.with(context).load(list.get(position)).into(viewHolder.binding.image);
//            viewHolder.binding.image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(null != mLitener){
//                        int pos = viewHolder.getLayoutPosition();
//                        mLitener.onItemClick(pos);
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        private ItemGalleryLayoutBinding binding;

        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickLitener mLitener){
        this.mLitener = mLitener;
    }
}
