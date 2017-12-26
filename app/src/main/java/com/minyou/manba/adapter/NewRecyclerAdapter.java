package com.minyou.manba.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.activity.ImageViewerActivity;
import com.minyou.manba.activity.PersonContentActivity;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;
import com.minyou.manba.util.OnItemClickLitener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
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

    private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;
    private LinearLayoutManager manager;

    private RequestManager glideRequest;

    @BindString(R.string.home_guanzhu_done)
    String home_guanzhu_done;


    public NewRecyclerAdapter(Context context, List<ZoneListResultModel.ResultBean.ZoneListBean> list) {
        this.context = context;
        if (null == list) {
            list = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
        } else {
            this.zoneList = list;
        }
        glideRequest = Glide.with(context);
//        manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false){
//            @Override
//            public boolean canScrollHorizontally() {
//                return false;
//            }
//        };
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

            // 加载用户图片
            if (TextUtils.isEmpty(zoneList.get(position).getUserPhotoUrl())) {
                glideRequest.load(R.drawable.login_icon_qq).transform(new GlideCircleTransform(context)).into(mItemViewHolder.iv_user_pic);
            } else {
                glideRequest.load(zoneList.get(position).getUserPhotoUrl()).transform(new GlideCircleTransform(context)).into(mItemViewHolder.iv_user_pic);
            }
            // 加载用户性别
            if (zoneList.get(position).getSex() == 1) {
                glideRequest.load(R.drawable.home_icon_nan).into(mItemViewHolder.iv_sex);
            } else {
                glideRequest.load(R.drawable.home_icon_women).into(mItemViewHolder.iv_sex);
            }
            // 加载用户名字
            mItemViewHolder.tv_username.setText(zoneList.get(position).getNickName());

            // 加载发布时间
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
            Date date = new Date(zoneList.get(position).getPublishTime());
            mItemViewHolder.tv_pub_time.setText(format.format(date));
            // 加载公会名称
            if(TextUtils.isEmpty(zoneList.get(position).getGuildName())){
                mItemViewHolder.tv_familyname.setVisibility(View.GONE);
            }else{
                mItemViewHolder.tv_familyname.setText(zoneList.get(position).getGuildName());
            }
            // TODO 加载发布图片
            //mItemViewHolder.rv_content_pics.setLayoutManager(manager);
            final List<String> zoneImages = zoneList.get(position).getZoneImage();

            if(null != zoneImages && zoneImages.size() > 0){
                mItemViewHolder.multiImagView.setVisibility(View.VISIBLE);
                mItemViewHolder.multiImagView.setList(zoneImages);
            }else{
                mItemViewHolder.multiImagView.setVisibility(View.GONE);
            }

            // 点击图片
            mItemViewHolder.multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, ImageViewerActivity.class);
                    intent.putExtra("position",position);
                    intent.putStringArrayListExtra("imageList", (ArrayList<String>) zoneImages);
                    context.startActivity(intent);
                }
            });

            // 加载内容
            mItemViewHolder.tv_content_desc.setText(zoneList.get(position).getZoneContent());
            // 加载次数
            mItemViewHolder.tv_content_fenxiang.setText((int) (Math.random() * 100) + "");
            mItemViewHolder.tv_content_zan.setText(zoneList.get(position).getUpvoteNum() + "");
            mItemViewHolder.tv_content_comment.setText(zoneList.get(position).getCommentNum() + "");
            // 设置点击事件
            // 关注按钮点击事件
            mItemViewHolder.tv_guanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemViewHolder.tv_guanzhu.setText(context.getResources().getString(R.string.home_guanzhu_done));
                    mItemViewHolder.tv_guanzhu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    // TODO 关注操作
                }
            });
            // 头像点击事件
            mItemViewHolder.rl_user_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonContentActivity.class);
                    intent.putExtra("userID",zoneList.get(position).getUserId());
                    context.startActivity(intent);
                }
            });

            // 点击分享按钮
            mItemViewHolder.tv_content_fenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "点击分享", Toast.LENGTH_SHORT).show();
                }
            });
            // 点击赞按钮
            mItemViewHolder.tv_content_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "点击赞按钮", Toast.LENGTH_SHORT).show();
                }
            });
            // 点击评论按钮
//            mItemViewHolder.tv_content_comment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "点击评论按钮", Toast.LENGTH_SHORT).show();
//                }
//            });
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
        if (null != mOnItemClickLitener) {
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
        return zoneList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setOnItemClick(OnItemClickLitener listener) {
        this.mOnItemClickLitener = listener;
    }

    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_user_pic)
        RelativeLayout rl_user_pic;
        @BindView(R.id.iv_user_pic)
        ImageView iv_user_pic;
        @BindView(R.id.iv_sex)
        ImageView iv_sex;
        @BindView(R.id.tv_username)
        TextView tv_username;
        @BindView(R.id.tv_pub_time)
        TextView tv_pub_time;
        @BindView(R.id.tv_familyname)
        TextView tv_familyname;
        @BindView(R.id.tv_guanzhu)
        TextView tv_guanzhu;
        @BindView(R.id.tv_content_desc)
        TextView tv_content_desc;
        @BindView(R.id.tv_content_fenxiang)
        TextView tv_content_fenxiang;
        @BindView(R.id.tv_content_zan)
        TextView tv_content_zan;
        @BindView(R.id.tv_content_comment)
        TextView tv_content_comment;
        @BindView(R.id.multiImagView)
        MultiImageView multiImagView;


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
