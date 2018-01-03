package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.adapter.CommentListAdapter;
import com.minyou.manba.databinding.ActivityDongtaiDetailBinding;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.CommentListResultModel;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.network.resultModel.ZoneDetailResultModel;
import com.minyou.manba.ui.view.DefalutRefreshView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Created by Administrator on 2017/11/26.
 */
public class DongTaiDetailActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "DongTaiDetailActivity";
    private ActivityDongtaiDetailBinding binding;
    private RequestManager glideRequest;
    private String info_id = null;
    private String user_id = null;

    private int pageSize = 10;
    private int pageNo = 1;

    private ZoneDetailResultModel.ZoneDetailBean zoneDetailBean;
    private CommentListAdapter mAdapter;
    private List<CommentListResultModel.ResultBean.CommentItemBean> commentItemBeanList;
    private View footView;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dongtai_detail);
        glideRequest = Glide.with(this);
        Intent intent = getIntent();
        if (null != intent) {
            info_id = intent.getStringExtra("id");
            user_id = intent.getStringExtra("userID");
        }
        showUI();
        // 获取动态详情
        getDongTaiInfo(info_id);
        // 获取点赞列表
        getZanList(info_id);
        // 获取评论列表

        getCommnetList(info_id);

        initListener();
    }

    private void showUI() {
        binding.atvTitle.setTitle(getResources().getString(R.string.detail_title));
        // 设置右上角按钮
        binding.atvTitle.setRightToDo(getResources().getString(R.string.detail_right), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DongTaiDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });

        commentItemBeanList = new ArrayList<CommentListResultModel.ResultBean.CommentItemBean>();
        mAdapter = new CommentListAdapter(this, commentItemBeanList);
        binding.recyclerComment.setLayoutManager(new LinearLayoutManager(this));
//        binding.recyclerComment.setLayoutManager(new FullyLinearLayoutManager(this));
        binding.recyclerComment.setAdapter(mAdapter);
        footView = new DefalutRefreshView(this);
        binding.pcflRefreshComment.setFooterView(footView);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initListener() {
        binding.tvZan.setOnClickListener(this);
        binding.tvShoucang.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvSend.setOnClickListener(this);
        binding.recyclerComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                binding.llComment.setVisibility(View.GONE);
                binding.rlMenu.setVisibility(View.VISIBLE);
                CommonUtil.closeKeybord(binding.etCommentText, binding.etCommentText.getContext());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        // 分页加载
        binding.pcflRefreshComment.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return !binding.pcflRefreshComment.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                loadMoreComment();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });

        // 点击评论条目
        mAdapter.setOnItemClickedListener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                LogUtil.d(TAG,"id=====" + commentItemBeanList.get(position).getCommentId());
            }
        });
    }

    /**
     * 获取更多评论
     */
    private void loadMoreComment() {
        pageNo ++;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("zoneId", info_id);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                CommentListResultModel commentListResultModel = new Gson().fromJson(result, CommentListResultModel.class);
                if (commentListResultModel.isSuccess() && commentListResultModel.getResult().getResultList().size() > 0) {
                    binding.pcflRefreshComment.refreshComplete();
                    commentItemBeanList.addAll(commentListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }else{
                    binding.pcflRefreshComment.refreshComplete();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                binding.pcflRefreshComment.refreshComplete();
            }
        });
    }

    /**
     * 获取评论列表
     *
     * @param info_id
     */
    private void getCommnetList(String info_id) {
        pageNo = 1;
        commentItemBeanList.clear();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("zoneId", info_id);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                CommentListResultModel commentListResultModel = new Gson().fromJson(result, CommentListResultModel.class);
                if (commentListResultModel.isSuccess() && commentListResultModel.getResult().getResultList().size() > 0) {
                    commentItemBeanList.addAll(commentListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                    binding.tvEmpty.setVisibility(View.GONE);
                    binding.pcflRefreshComment.refreshComplete();
                }else{
                    footView.setVisibility(View.GONE);
                    binding.pcflRefreshComment.refreshComplete();
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    /**
     * 获取点赞列表
     *
     * @param info_id
     */
    private void getZanList(String info_id) {
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTELIST + "/" + info_id, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserZanListResultModel userZanListResultModel = new Gson().fromJson(result, UserZanListResultModel.class);
                if (userZanListResultModel.isSuccess()) {
                    List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList = userZanListResultModel.getResult();
                    mAdapter.setZanList(userZanListInnerBeanList);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }


    /**
     * 获取动态详情
     *
     * @param info_id
     */
    private void getDongTaiInfo(String info_id) {
        loading();
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user_id);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_DETAIL + "/" + info_id, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneDetailResultModel zoneDetailResultModel = new Gson().fromJson(result, ZoneDetailResultModel.class);
                if (null != zoneDetailResultModel) {
                    zoneDetailBean = zoneDetailResultModel.getResult();
                    binding.setZoneDetailBean(zoneDetailBean);
                }
                cancelLoading();
                if (mAdapter != null) {
                    mAdapter.setZoneDetailBean(zoneDetailResultModel.getResult());
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        HashMap<String, String> params;
        switch (v.getId()) {
            case R.id.tv_zan:
                if (binding.tvZan.isChecked()) {// 点赞
                    zoneDetailBean.setUpvote(true);
                    zoneDetailBean.setUpvoteNum(zoneDetailBean.getUpvoteNum() + 1);
                } else {
                    zoneDetailBean.setUpvote(false);
                    zoneDetailBean.setUpvoteNum(zoneDetailBean.getUpvoteNum() - 1);
                }

                // 通知列表页更新数据
                EventInfo info = new EventInfo();
                info.setType(EventInfo.ZONE_UPDATE_ZAN);
                EventBus.getDefault().post(info);

                params = new HashMap<String, String>();
                params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                params.put("zoneId", String.valueOf(zoneDetailBean.getId()));
                ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTE, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                    @Override
                    public void onReqSuccess(String result) {

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });

                break;
            case R.id.tv_shoucang:
                if (binding.tvShoucang.isChecked()) {
                    zoneDetailBean.setFavorite(true);
                    zoneDetailBean.setFavoriteNum(zoneDetailBean.getFavoriteNum() + 1);
                } else {
                    zoneDetailBean.setFavorite(false);
                    zoneDetailBean.setFavoriteNum(zoneDetailBean.getFavoriteNum() - 1);
                }
                params = new HashMap<String, String>();
                params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                params.put("zoneId", String.valueOf(zoneDetailBean.getId()));
                ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_FAVORITE, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                    @Override
                    public void onReqSuccess(String result) {

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });
                break;
            case R.id.tv_comment:
                binding.llComment.setVisibility(View.VISIBLE);
                binding.rlMenu.setVisibility(View.GONE);
                break;

            case R.id.tv_send:
                loading();
                if (TextUtils.isEmpty(binding.etCommentText.getText().toString())) {
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    params = new HashMap<String, String>();
                    params.put("commentParentId", "");
                    params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                    params.put("zoneId", zoneDetailBean.getId() + "");
                    params.put("content", binding.etCommentText.getText().toString());
                    ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENT, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                        @Override
                        public void onReqSuccess(String result) {
                            cancelLoading();
                            binding.llComment.setVisibility(View.GONE);
                            binding.rlMenu.setVisibility(View.VISIBLE);
                            // TODO 更新评论列表数据
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {
                            cancelLoading();
                        }
                    });
                }
                break;
        }
    }
}
