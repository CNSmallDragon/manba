package com.minyou.manba.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.ImageViewerActivity;
import com.minyou.manba.databinding.ItemActivityDongtaiDetailBinding;
import com.minyou.manba.databinding.ItemCommentListBinding;
import com.minyou.manba.databinding.LoadMoreFootviewLayoutBinding;
import com.minyou.manba.fragment.NewFragment;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.resultModel.CommentListResultModel;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.network.resultModel.ZoneDetailResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.DateFormatUtil;
import com.minyou.manba.util.OnItemClickLitener;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.databinding.DataBindingUtil.inflate;

/**
 * Created by Administrator on 2018/1/2.
 */

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommentListAdapter";

    private static final int ITEM_TYPE_HEADER = 1001;//头视图
    private static final int ITEM_TYPE_NORMAL = 1002;//头视图
    private static final int ITEM_TYPE_FOOTER = 1003;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;

    private List<CommentListResultModel.ResultBean.CommentItemBean> list;
    private Context context;

    private ZoneDetailResultModel.ZoneDetailBean itemInfo;
    private List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList;

    public CommentListAdapter(Context context,List<CommentListResultModel.ResultBean.CommentItemBean> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_HEADER){   // 头视图
            ItemActivityDongtaiDetailBinding headBinding = inflate(LayoutInflater.from(context), R.layout.item_activity_dongtai_detail,parent,false);
            HeadViewHolder headViewHolder = new HeadViewHolder(headBinding.getRoot());
            headViewHolder.headBinding = headBinding;
            return headViewHolder;
        }else if(viewType == ITEM_TYPE_NORMAL){
            ItemCommentListBinding binding = inflate(LayoutInflater.from(context), R.layout.item_comment_list,parent,false);
            CommentViewHolder commentViewHolder = new CommentViewHolder(binding.getRoot());
            commentViewHolder.binding = binding;
            return commentViewHolder;
        }else if(viewType == ITEM_TYPE_FOOTER){
            LoadMoreFootviewLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.load_more_footview_layout,parent,false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(binding.getRoot());
            footerViewHolder.binding = binding;
            return footerViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof HeadViewHolder){
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            if(itemInfo != null){
                // 设置头像显示
                NewFragment.setUserPicList(headViewHolder.headBinding.rlUserPic,itemInfo.toZoneListBean());
                headViewHolder.headBinding.tvUsername.setText(itemInfo.getNickName());
                // 加载发布时间
                headViewHolder.headBinding.tvPubTime.setText(DateFormatUtil.format(itemInfo.getPublishTime()));
                if (TextUtils.isEmpty(itemInfo.getGuildName())) {
                    headViewHolder.headBinding.tvFamilyname.setVisibility(View.GONE);
                } else {
                    headViewHolder.headBinding.tvFamilyname.setText(itemInfo.getGuildName());
                }
                // 关注按钮点击事件
                NewFragment.setDongTaiGuanZhu(headViewHolder.headBinding.tvGuanzhu,itemInfo.toZoneListBean());
                // 显示点赞数量
                headViewHolder.headBinding.tvCountZan.setText(String.valueOf(itemInfo.getUpvoteNum()));
                // 显示评论数量
                headViewHolder.headBinding.allReplyNumTv.setText(String.format(context.getResources().getString(R.string.comment_number),list.size()+""));
                // 加载发布图片
                if(null != itemInfo.getZoneImage() && itemInfo.getZoneImage().size() > 0){
                    headViewHolder.headBinding.multiImagView.setList(itemInfo.getZoneImage());
                }
                headViewHolder.headBinding.multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, ImageViewerActivity.class);
                        intent.putExtra("position", position);
                        intent.putStringArrayListExtra("imageList", (ArrayList<String>) itemInfo.getZoneImage());
                        context.startActivity(intent);
                    }
                });
                // 加载内容
                headViewHolder.headBinding.tvContentDesc.setText(itemInfo.getZoneContent());
                // 获取点赞列表
                if(null != userZanListInnerBeanList){
                    headViewHolder.headBinding.llZanList.removeAllViews();
                    if(userZanListInnerBeanList.size() > 0 && userZanListInnerBeanList.size() < 6){
                        headViewHolder.headBinding.ivMore.setVisibility(View.GONE);
                        for(UserZanListResultModel.UserZanListInnerBean bean : userZanListInnerBeanList){
                            ImageView imageView = new ImageView(headViewHolder.headBinding.llZanList.getContext());
                            // 计算宽高
                            int screenWidth = CommonUtil.getScreenWidth(headViewHolder.headBinding.llZanList.getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/9,screenWidth/9);
                            params.setMarginStart(CommonUtil.dip2px(headViewHolder.headBinding.llZanList.getContext(),10));
                            imageView.setLayoutParams(params);
                            if(TextUtils.isEmpty(bean.getPhotoUrl())){
                                Glide.with(headViewHolder.headBinding.llZanList.getContext()).load(R.drawable.register_home_pre).into(imageView);
                            }else{
                                Glide.with(headViewHolder.headBinding.llZanList.getContext()).load(bean.getPhotoUrl()).transform(new GlideCircleTransform(headViewHolder.headBinding.llZanList.getContext())).into(imageView);
                            }
                            headViewHolder.headBinding.llZanList.addView(imageView);
                        }
                    }else if(userZanListInnerBeanList.size() > 6){
                        for(int i=0;i <= 5;i++){
                            ImageView imageView = new ImageView(headViewHolder.headBinding.llZanList.getContext());
                            // 计算宽高
                            int screenWidth = CommonUtil.getScreenWidth(headViewHolder.headBinding.llZanList.getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/9,screenWidth/9);
                            imageView.setLayoutParams(params);
                            if(TextUtils.isEmpty(userZanListInnerBeanList.get(i).getPhotoUrl())){
                                Glide.with(headViewHolder.headBinding.llZanList.getContext()).load(R.drawable.register_home_pre).into(imageView);
                            }else{
                                Glide.with(headViewHolder.headBinding.llZanList.getContext()).load(userZanListInnerBeanList.get(i).getPhotoUrl()).transform(new GlideCircleTransform(headViewHolder.headBinding.llZanList.getContext())).into(imageView);
                            }
                            headViewHolder.headBinding.llZanList.addView(imageView);
                        }
                        headViewHolder.headBinding.ivMore.setVisibility(View.VISIBLE);
                    }else{
                        headViewHolder.headBinding.ivMore.setVisibility(View.GONE);
                    }
                }

                // 选择排序方式
                headViewHolder.headBinding.tvSortType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortTypeChangedListener.setSortTypeChangedListener();
                    }
                });

            }

        }else if(holder instanceof CommentViewHolder){
            final CommentViewHolder viewHolder = (CommentViewHolder) holder;
            viewHolder.binding.setCommentItemBean(list.get(position));
            viewHolder.binding.executePendingBindings();
            // 加载头像图片
            if(TextUtils.isEmpty(list.get(position).getPhotoUrl())){
                Glide.with(context).load(R.drawable.register_home_pre).into(viewHolder.binding.listHeadImage);
            }else{
                Glide.with(context).load(list.get(position).getPhotoUrl()).transform(new GlideCircleTransform(context)).into(viewHolder.binding.listHeadImage);
            }

            // 评论点赞
            viewHolder.binding.cbCommentZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolder.binding.cbCommentZan.isChecked()){    // 点赞
                        list.get(position).setUpvote(true);
                    }else{
                        list.get(position).setUpvote(false);
                    }
                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                    params.put("commentId",list.get(position).getCommentId()+"");
                    ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_ZONE_COMMENTUPVOTE, ManBaRequestManager.TYPE_POST_JSON, params, null);
                }
            });

            // item点击事件
            if(null != mLitener){
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getLayoutPosition();
                        mLitener.onItemClick(pos);
                    }
                });
            }
        }else if(holder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus){
                case PULLUP_LOAD_MORE:
                    //上拉加载更多
                    //footerViewHolder.tvLoadText.setText(loadmore);
                    footerViewHolder.binding.tvLoadText.setText(context.getResources().getString(R.string.gonghui_loadmore));
                    break;
                case LOADING_MORE:
                    //footerViewHolder.tvLoadText.setText(loading);
                    footerViewHolder.binding.tvLoadText.setText(context.getResources().getString(R.string.gonghui_loading));
                    //正在加载中
                    break;
                case NO_LOAD_MORE:
                    //没有加载更多 隐藏
                    footerViewHolder.binding.loadLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size(); // 显示头view
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == getItemCount()) {
//            return ITEM_TYPE_FOOTER;
//        } else {
//            return ITEM_TYPE_NORMAL;
//        }
        return ITEM_TYPE_NORMAL;
    }

    public void setZoneDetailBean(ZoneDetailResultModel.ZoneDetailBean itemInfo){
        this.itemInfo = itemInfo;
    }

    public void setZanList(List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList){
        this.userZanListInnerBeanList = userZanListInnerBeanList;
    }

    private OnItemClickLitener mLitener;            // 条目点击事件
    private SortTypeChangedListener sortTypeChangedListener;        // 排序点击事件

    public void setOnItemClickedListener(OnItemClickLitener mLitener){
        this.mLitener = mLitener;
    }

    public interface SortTypeChangedListener{
        void setSortTypeChangedListener();
    }

    public void setSortTypeChangedListener(SortTypeChangedListener listener){
        this.sortTypeChangedListener = listener;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{

        private ItemCommentListBinding binding;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{

        private ItemActivityDongtaiDetailBinding headBinding;

        public ItemActivityDongtaiDetailBinding getHeadBinding() {
            return headBinding;
        }

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        private LoadMoreFootviewLayoutBinding binding;

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}
