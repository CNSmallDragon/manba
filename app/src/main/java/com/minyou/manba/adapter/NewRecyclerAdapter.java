package com.minyou.manba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.bean.ItemInfo;
import com.minyou.manba.util.GlideCircleTransform;
import com.minyou.manba.util.OnItemClickLitener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/6.
 */
public class NewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NewRecyclerAdapter";
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

    private List<ItemInfo> list;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;

    private RequestManager glideRequest;


    public NewRecyclerAdapter(Context context, List<ItemInfo> list) {
        this.context = context;
        if (null == list) {
            list = new ArrayList<ItemInfo>();
        } else {
            this.list = list;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_new, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.load_more_footview_layout, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            // 加载用户图片
            glideRequest = Glide.with(context);
            if(TextUtils.isEmpty(list.get(position).getUserPic())){
                glideRequest.load(R.drawable.login_icon_qq).transform(new GlideCircleTransform(context)).into(viewHolder.iv_user_pic);
            }else{
                glideRequest.load(list.get(position).getUserPic()).transform(new GlideCircleTransform(context)).into(viewHolder.iv_user_pic);
            }
            // 加载用户名字
            viewHolder.tv_username.setText(list.get(position).getUserName());
            // 加载发布时间
            SimpleDateFormat format=new SimpleDateFormat("yy/MM/dd HH:mm");
            Date date=new Date(list.get(position).getDate());
            viewHolder.tv_pub_time.setText(format.format(date));
            // 加载家族名称
            viewHolder.tv_familyname.setText(list.get(position).getFamilyName());
            // 加载发布图片
            if(TextUtils.isEmpty(list.get(position).getPhotoUrl())){
                glideRequest.load(R.drawable.test).into(viewHolder.iv_content_pic);
            }else{
                glideRequest.load(list.get(position).getPhotoUrl()).into(viewHolder.iv_content_pic);
            }
            // 加载内容
            viewHolder.tv_content_desc.setText(list.get(position).getContentStr());
            // 加载次数
            viewHolder.tv_content_fenxiang.setText(list.get(position).getSharedCount() + "");
            viewHolder.tv_content_zan.setText(list.get(position).getZanCount() + "");
            viewHolder.tv_content_comment.setText(list.get(position).getCommentCount() + "");
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.tvLoadText.setText(context.getResources().getString(R.string.gonghui_loadmore));
                    break;
                case LOADING_MORE:
                    footerViewHolder.tvLoadText.setText(context.getResources().getString(R.string.gonghui_loading));
                    break;
                case NO_LOAD_MORE:
                    footerViewHolder.loadLayout.setVisibility(View.GONE);
                    break;
            }
        }
        if(null != mOnItemClickLitener){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setOnItemClick(OnItemClickLitener listener){
        this.mOnItemClickLitener = listener;
    }

    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_pic)
        ImageView iv_user_pic;
        @BindView(R.id.tv_username)
        TextView tv_username;
        @BindView(R.id.tv_pub_time)
        TextView tv_pub_time;
        @BindView(R.id.tv_familyname)
        TextView tv_familyname;
        @BindView(R.id.tv_guanzhu)
        TextView tv_guanzhu;
        @BindView(R.id.iv_content_pic)
        ImageView iv_content_pic;
        @BindView(R.id.tv_content_desc)
        TextView tv_content_desc;
        @BindView(R.id.tv_content_fenxiang)
        TextView tv_content_fenxiang;
        @BindView(R.id.tv_content_zan)
        TextView tv_content_zan;
        @BindView(R.id.tv_content_comment)
        TextView tv_content_comment;


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
}
