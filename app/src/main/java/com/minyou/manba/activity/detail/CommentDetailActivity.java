package com.minyou.manba.activity.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.DataBindingBaseActivity;
import com.minyou.manba.databinding.ActivityCommentDetailBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.CommentListResultModel;
import com.minyou.manba.network.resultModel.ReplyCommentListResultModel;
import com.minyou.manba.ui.view.DefalutRefreshView;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/3.
 */

public class CommentDetailActivity extends DataBindingBaseActivity {

    private static final String TAG = "CommentDetailActivity";
    private int pageSize = 20;
    private int pageNo = 1;

    private ActivityCommentDetailBinding binding;
    private CommentListResultModel.ResultBean.CommentItemBean commentItem;
    private String userId = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);

    private View footView;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_detail);
        Intent intent = getIntent();
        if(null != intent){
            commentItem = intent.getParcelableExtra("commentItem");
            binding.setCommentItemBean(commentItem);
        }
        initView();
        getData();
        initListener();
    }

    private void initListener() {

    }

    private void initView() {
        // 显示楼主头像
        if(TextUtils.isEmpty(commentItem.getPhotoUrl())){
            Glide.with(this).load(R.drawable.register_home_pre).into(binding.commentDetailHeadImage);
        }else{
            Glide.with(this).load(commentItem.getPhotoUrl()).transform(new GlideCircleTransform(this)).into(binding.commentDetailHeadImage);
        }
        // 设置title
        binding.actCommentTitle.setTitle(String.format(getResources().getString(R.string.comment_detail_num),commentItem.getCommentId()+""));
        binding.recyclerCommentDetail.setLayoutManager(new LinearLayoutManager(this));

        footView = new DefalutRefreshView(this);
        binding.pcflRefreshCommentDetail.setFooterView(footView);
    }

    public void getData() {
        pageNo = 1;
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("zoneId",commentItem.getZoneId()+"");
        params.put("parentId",commentItem.getCommentId()+"");
        params.put("userId",userId);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_REPLYCOMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ReplyCommentListResultModel replyCommentListResultModel = new Gson().fromJson(result,ReplyCommentListResultModel.class);
                if(replyCommentListResultModel.isSuccess() && replyCommentListResultModel.getResult().getResultList().size() > 0){
                    binding.pcflRefreshCommentDetail.refreshComplete();
                    footView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }
}
