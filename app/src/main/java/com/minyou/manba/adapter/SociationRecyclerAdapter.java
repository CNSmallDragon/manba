package com.minyou.manba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.activity.LoginActivity;
import com.minyou.manba.bean.SociationBean;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/2.
 */
public class SociationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SociationRecyclerAdapter";
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    @BindString(R.string.gonghui_loadmore)
    String loadmore;
    @BindString(R.string.gonghui_loading)
    String loading;

    private Context context;
    private List<SociationBean> list;

    private RequestManager glideRequest;
    private OnItemClickLitener mOnItemClickLitener;

    public SociationRecyclerAdapter(Context context, List<SociationBean> list) {
        this.context = context;
        if (null == list) {
            list = new ArrayList<SociationBean>();
        } else {
            this.list = list;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_ITEM) {
            //View view = LinearLayout.inflate(context, R.layout.item_gonghui_list, null);
            View view = LayoutInflater.from(context).inflate(R.layout.item_gonghui_list, viewGroup,false);
            return new ItemViewHolder(view);
        } else {
            //View view = LinearLayout.inflate(context, R.layout.load_more_footview_layout, null);
            View view = LayoutInflater.from(context).inflate(R.layout.load_more_footview_layout, viewGroup,false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        LogUtil.d(TAG,"i===========" + i);
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder mViewHolder = (ItemViewHolder) viewHolder;
            glideRequest = Glide.with(context);
            glideRequest.load(R.drawable.ic_launcher).transform(new GlideCircleTransform(context)).into(mViewHolder.gonghui_pic);
            mViewHolder.gonghui_name.setText(list.get(i).getName());
            mViewHolder.gonghui_member_num.setText("成员数：" + list.get(i).getMemberNum());
            mViewHolder.gonghui_hot_num.setText("活跃度" + list.get(i).getMemberNum());
            mViewHolder.gonghui_join.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(CommonUtil.isLogin()){
                        // 已经登陆
                        Toast.makeText(context, "已加入", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });

            if (i == 0) {
                mViewHolder.gonghui_top.setBackground(context.getResources().getDrawable(R.drawable.guild_label_first));
                mViewHolder.gonghui_top.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                mViewHolder.gonghui_top.setBackground(context.getResources().getDrawable(R.drawable.guild_label_second));
                mViewHolder.gonghui_top.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                mViewHolder.gonghui_top.setBackground(context.getResources().getDrawable(R.drawable.guild_label_third));
                mViewHolder.gonghui_top.setVisibility(View.VISIBLE);
            } else {
                mViewHolder.gonghui_top.setBackground(null);
                mViewHolder.gonghui_top.setVisibility(View.GONE);
            }

            if (null != mOnItemClickLitener) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = viewHolder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(position);
                    }
                });
            }
        }else if(viewHolder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            switch (mLoadMoreStatus){
                case PULLUP_LOAD_MORE:
                    //上拉加载更多
                    //footerViewHolder.tvLoadText.setText(loadmore);
                    footerViewHolder.tvLoadText.setText(context.getResources().getString(R.string.gonghui_loadmore));
                    break;
                case LOADING_MORE:
                    //footerViewHolder.tvLoadText.setText(loading);
                    footerViewHolder.tvLoadText.setText(context.getResources().getString(R.string.gonghui_loading));
                    //正在加载中
                    break;
                case NO_LOAD_MORE:
                    //没有加载更多 隐藏
                    ((FooterViewHolder) viewHolder).loadLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        LogUtil.d(TAG,"position===========" + position);
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setOnItemClickListener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_gonghui_top)
        ImageView gonghui_top;
        @BindView(R.id.iv_gonghui_pic)
        ImageView gonghui_pic;
        @BindView(R.id.tv_gonghui_name)
        TextView gonghui_name;
        @BindView(R.id.tv_gonghui_member_num)
        TextView gonghui_member_num;
        @BindView(R.id.tv_gonghui_hot_num)
        TextView gonghui_hot_num;
        @BindView(R.id.tv_gonghui_join)
        TextView gonghui_join;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loadLayout)
        LinearLayout loadLayout;
        @BindView(R.id.pbLoad)
        ProgressBar pbLoad;
        @BindView(R.id.tvLoadText)
        TextView tvLoadText;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}
