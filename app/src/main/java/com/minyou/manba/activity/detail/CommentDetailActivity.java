package com.minyou.manba.activity.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.DataBindingBaseActivity;
import com.minyou.manba.adapter.CommentDetailAdapter;
import com.minyou.manba.databinding.ActivityCommentDetailBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.CommentListResultModel;
import com.minyou.manba.network.resultModel.ReplyCommentListResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.HeadZoomScrollView;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class CommentDetailActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "CommentDetailActivity";
    private int pageSize = 20;
    private int pageNo = 1;

    private ActivityCommentDetailBinding binding;
    private CommentListResultModel.ResultBean.CommentItemBean commentItem;
    private String userId = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);

    private CommentDetailAdapter mAdapter;
    private LinearLayoutManager manager;
    private List<ReplyCommentListResultModel.ResultBean.ReplyCommentListBean> list = new ArrayList<ReplyCommentListResultModel.ResultBean.ReplyCommentListBean>();

    private int floorMasterId;      // 楼主ID
    private int replyUserId = 0;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_detail);
        Intent intent = getIntent();
        if(null != intent){
            commentItem = intent.getParcelableExtra("commentItem");
            LogUtil.d(TAG,new Gson().toJson(commentItem));
            floorMasterId = commentItem.getCommentUserId();
            binding.setCommentItemBean(commentItem);
        }
        initView();
        getData();
        initListener();
    }

    private void initListener() {
        // 编辑框获取焦点
        binding.etCommentText.setOnClickListener(this);
        // 发送按钮点击事件
        binding.tvSend.setOnClickListener(this);
        // 点击空白区域隐藏输入框
        binding.bottomLay.setOnClickListener(this);

        binding.commentScrollview.setOnScrollListener(new HeadZoomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                replyUserId = 0;
                binding.etCommentText.setHint(getResources().getString(R.string.detail_send_hint));
                binding.llComment.setVisibility(View.GONE);
            }
        });

        // item点击事件
        mAdapter.setOnItemClickLinsener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                ReplyCommentListResultModel.ResultBean.ReplyCommentListBean replyCommentListBean = list.get(position);
                if(null != replyCommentListBean){
                    if(userId.equals(String.valueOf(replyCommentListBean.getCommentUserId()))){
                        // 自己回复别人的，输入框里显示后面人的昵称
                        if(replyCommentListBean.getReplyUserId() != floorMasterId){
                            binding.llComment.setVisibility(View.VISIBLE);
                            binding.etCommentText.setHint("@" + replyCommentListBean.getReplyUserName());
                            replyUserId = replyCommentListBean.getReplyUserId();
                        }else{
                            // 如果是自己回复楼主
                            replyUserId = 0;
                            binding.etCommentText.setHint(getResources().getString(R.string.detail_send_hint));
                            binding.llComment.setVisibility(View.VISIBLE);
                        }
                    }else{
                        binding.llComment.setVisibility(View.VISIBLE);
                        binding.etCommentText.setHint("@" + replyCommentListBean.getCommentUserName());
                        replyUserId = replyCommentListBean.getCommentUserId();
                    }
                }
            }
        });

        binding.recyclerCommentDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1 == mAdapter.getItemCount()){
                    // TODO
                    LogUtil.d(TAG,"loadmore-------------------");
                    loadMoreComment();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMoreComment() {
        pageNo++;
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("zoneId",commentItem.getZoneId()+"");
        params.put("rootId",commentItem.getCommentId()+"");
        params.put("userId",userId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_REPLYCOMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                ReplyCommentListResultModel replyCommentListResultModel = new Gson().fromJson(result,ReplyCommentListResultModel.class);
                if(replyCommentListResultModel.isSuccess() && replyCommentListResultModel.getResult().getResultList().size() > 0){
                    list.addAll(replyCommentListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
            }
        });
    }

    private void initView() {
        // 显示楼主头像
        if(TextUtils.isEmpty(commentItem.getPhotoUrl())){
            Glide.with(this).load(R.drawable.register_home_pre).into(binding.commentDetailHeadImage);
        }else{
            Glide.with(this).load(commentItem.getPhotoUrl()).transform(new GlideCircleTransform(this)).into(binding.commentDetailHeadImage);
        }
        // 设置title
        binding.actCommentTitle.setTitle(String.format(getResources().getString(R.string.comment_detail_num),commentItem.getLevelNum()+""));
        binding.actCommentTitle.setRightToDo(getResources().getString(R.string.comment_pub), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyUserId = 0;
                binding.etCommentText.setHint(getResources().getString(R.string.detail_send_hint));
                binding.llComment.setVisibility(View.VISIBLE);
            }
        });
        binding.recyclerCommentDetail.setLayoutManager(new LinearLayoutManager(this));

        // 显示楼中楼
        manager = new LinearLayoutManager(this);
        mAdapter = new CommentDetailAdapter(this,list);
        binding.recyclerCommentDetail.setLayoutManager(manager);
        binding.recyclerCommentDetail.setAdapter(mAdapter);
        // 将楼主ID传过去
        mAdapter.setFloorMasterId(floorMasterId);

    }

    public void getData() {
        loading();
        pageNo = 1;
        list.clear();
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("zoneId",commentItem.getZoneId()+"");
        params.put("rootId",commentItem.getCommentId()+"");
        //params.put("parentId",commentItem.getCommentId()+"");
        params.put("userId",userId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_REPLYCOMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                ReplyCommentListResultModel replyCommentListResultModel = new Gson().fromJson(result,ReplyCommentListResultModel.class);
                if(replyCommentListResultModel.isSuccess() && replyCommentListResultModel.getResult().getResultList().size() > 0){
                    binding.commentDetailMore.setVisibility(View.VISIBLE);
                    list.addAll(replyCommentListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }else{
                    binding.commentDetailMore.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.et_comment_text:
                // 获取焦点，打开软键盘
                binding.etCommentText.setFocusable(true);
                break;
            case R.id.tv_send:
                sendCommentInfo();
                break;
            case R.id.bottom_lay:
                replyUserId = 0;
                binding.etCommentText.setHint(getResources().getString(R.string.detail_send_hint));
                binding.llComment.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 发布回复
     */
    private void sendCommentInfo() {
        loading();
        HashMap<String, String> params;
        if (TextUtils.isEmpty(binding.etCommentText.getText().toString())) {
            Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        } else {
            params = new HashMap<String, String>();
            params.put("commentParentId", String.valueOf(commentItem.getCommentId()));      // 父评论id
            params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
            if(replyUserId != 0){
                params.put("replyUserId", String.valueOf(replyUserId));
            }else{
                params.put("replyUserId", String.valueOf(commentItem.getCommentUserId()));
            }
            params.put("zoneId", commentItem.getZoneId() + "");
            params.put("content", binding.etCommentText.getText().toString());
            params.put("rootId", commentItem.getCommentId() + "");
            ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENT, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                @Override
                public void onReqSuccess(String result) {
                    binding.etCommentText.setText("");
                    getData();
                    cancelLoading();
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    cancelLoading();
                }
            });
        }
    }
}
