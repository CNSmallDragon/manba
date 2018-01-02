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
import com.minyou.manba.R;
import com.minyou.manba.activity.ImageViewerActivity;
import com.minyou.manba.databinding.ItemActivityDongtaiDetailBinding;
import com.minyou.manba.databinding.ItemCommentListBinding;
import com.minyou.manba.fragment.NewFragment;
import com.minyou.manba.network.resultModel.CommentListResultModel;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.network.resultModel.ZoneDetailResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommentListAdapter";

    protected final int ITEM_TYPE_HEADER = 1001;//头视图
    protected final int ITEM_TYPE_NORMAL = 1002;//头视图

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
            ItemActivityDongtaiDetailBinding headBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_activity_dongtai_detail,parent,false);
            HeadViewHolder headViewHolder = new HeadViewHolder(headBinding.getRoot());
            headViewHolder.headBinding = headBinding;
            return headViewHolder;
        }else if(viewType == ITEM_TYPE_NORMAL){
            ItemCommentListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_comment_list,parent,false);
            CommentViewHolder commentViewHolder = new CommentViewHolder(binding.getRoot());
            commentViewHolder.binding = binding;
            return commentViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            if(itemInfo != null){
                // 设置头像显示
                NewFragment.setUserPicList(headViewHolder.headBinding.rlUserPic,itemInfo.toZoneListBean());
                headViewHolder.headBinding.tvUsername.setText(itemInfo.getNickName());
                // 加载发布时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date date = new Date(itemInfo.getPublishTime());
                headViewHolder.headBinding.tvPubTime.setText(format.format(date));
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
                headViewHolder.headBinding.allReplyNumTv.setText(String.format(context.getResources().getString(R.string.comment_number),itemInfo.getCommentNum()+""));
                // 加载发布图片
                headViewHolder.headBinding.multiImagView.setList(itemInfo.getZoneImage());
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
                    }
                }

            }

        }else if(holder instanceof CommentViewHolder){
            LogUtil.d(TAG,"onBindViewHolder======" + position);
            CommentViewHolder viewHolder = (CommentViewHolder) holder;
            viewHolder.binding.setCommentItemBean(list.get(position - 1));
            viewHolder.binding.executePendingBindings();
            // 加载头像图片
            if(TextUtils.isEmpty(list.get(position - 1).getPhotoUrl())){
                Glide.with(context).load(R.drawable.register_home_pre).into(viewHolder.binding.listHeadImage);
            }else{
                Glide.with(context).load(list.get(position - 1).getPhotoUrl()).transform(new GlideCircleTransform(context)).into(viewHolder.binding.listHeadImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1; // 显示头view
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return ITEM_TYPE_HEADER;
        }else{
            return ITEM_TYPE_NORMAL;
        }
    }

    public void setZoneDetailBean(ZoneDetailResultModel.ZoneDetailBean itemInfo){
        this.itemInfo = itemInfo;
    }

    public void setZanList(List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList){
        this.userZanListInnerBeanList = userZanListInnerBeanList;
    }

    private OnItemClickLitener mLitener;

    public void setOnItemClickedListener(OnItemClickLitener mLitener){
        this.mLitener = mLitener;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{

        private ItemCommentListBinding binding;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{

        private ItemActivityDongtaiDetailBinding headBinding;

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
