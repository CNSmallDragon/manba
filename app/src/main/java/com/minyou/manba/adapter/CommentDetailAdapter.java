package com.minyou.manba.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.PersonContentActivity;
import com.minyou.manba.activity.detail.CommentDetailActivity;
import com.minyou.manba.databinding.ItemCommentFloorBinding;
import com.minyou.manba.network.resultModel.ReplyCommentListResultModel;
import com.minyou.manba.util.OnItemClickLitener;

import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */

public class CommentDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommentDetailAdapter";

    private Context context;
    private List<ReplyCommentListResultModel.ResultBean.ReplyCommentListBean> list;
    private OnItemClickLitener mLitener;

    private int floorMasterId;      // 楼主ID

    public CommentDetailAdapter(Context context,List<ReplyCommentListResultModel.ResultBean.ReplyCommentListBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentFloorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_comment_floor,parent,false);
        ReplyCommentViewHolder viewHolder = new ReplyCommentViewHolder(binding.getRoot());
        viewHolder.binding = binding;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ReplyCommentViewHolder){
            final ReplyCommentViewHolder viewHolder = (ReplyCommentViewHolder) holder;
            final ReplyCommentListResultModel.ResultBean.ReplyCommentListBean replyCommentListBean = list.get(position);
            viewHolder.binding.setReplyCommentListBean(replyCommentListBean);
            viewHolder.binding.setFloorMasterId(getFloorMasterId());

            // 条目点击事件
            viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getLayoutPosition();
                    mLitener.onItemClick(position);
                }
            });

            // 点击用户名事件
            viewHolder.binding.tvCommentUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent((CommentDetailActivity)context, PersonContentActivity.class);
                    intent.putExtra(Appconstant.PERSON_CENTER,replyCommentListBean.getCommentUserId());
                    context.startActivity(intent);
                }
            });

            viewHolder.binding.tvReplyUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent((CommentDetailActivity)context, PersonContentActivity.class);
                    intent.putExtra(Appconstant.PERSON_CENTER,replyCommentListBean.getReplyUserId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ReplyCommentViewHolder extends RecyclerView.ViewHolder{

        private ItemCommentFloorBinding binding;

        public ReplyCommentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setFloorMasterId(int floorMasterId) {
        this.floorMasterId = floorMasterId;
    }

    public int getFloorMasterId() {
        return floorMasterId;
    }

    public void setOnItemClickLinsener(OnItemClickLitener mLitener){
        this.mLitener = mLitener;
    }
}
