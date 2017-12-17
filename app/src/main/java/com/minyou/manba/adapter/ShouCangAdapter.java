package com.minyou.manba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.bean.ShouCangBean;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.ui.view.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/2.
 */
public class ShouCangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShouCangBean> list;
    private Context context;
    private LayoutInflater inflater;
    private RequestManager glideRequest;

    public ShouCangAdapter(Context context,List<ShouCangBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        glideRequest = Glide.with(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_shoucang,parent,false);
        return new ShouCHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ShouCHolder){
            ShouCHolder viewHolder = (ShouCHolder) holder;
            ShouCangBean bean = list.get(position);
            if(TextUtils.isEmpty(bean.getUser_pic())){
                glideRequest.load(R.drawable.login_icon_qq).into(viewHolder.iv_user_pic);
            }else {
                glideRequest.load(bean.getUser_pic()).transform(new GlideCircleTransform(context)).into(viewHolder.iv_user_pic);
            }
            viewHolder.tv_user_name.setText(bean.getUser_name());
            viewHolder.tv_time.setText(CommonUtil.transformTime(bean.getShouc_time()));
            if(TextUtils.isEmpty(bean.getPub_pic())){
                viewHolder.iv_pic.setVisibility(View.GONE);
            }else{
                glideRequest.load(bean.getPub_pic()).into(viewHolder.iv_pic);
            }
            viewHolder.tv_content.setText(bean.getPub_content());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ShouCHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_user_pic)
        ImageView iv_user_pic;
        @BindView(R.id.tv_user_name)
        TextView tv_user_name;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.iv_pic)
        ImageView iv_pic;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ShouCHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
