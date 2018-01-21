package com.minyou.manba.activity;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.ActivityFollowListBinding;
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.CommonUserListResultModel;
import com.minyou.manba.ui.RefreshItemDecoration;
import com.minyou.manba.ui.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luchunhao on 2018/1/17.
 */

public class FollowListActivity extends DataBindingBaseActivity {

    private static final String TAG = "FollowListActivity";

    private ActivityFollowListBinding binding;
    private LinearLayoutManager manager;
    private MyMvvmAdapter<CommonUserListResultModel.ResultBean.UserListBean> mAdapter;
    private List<CommonUserListResultModel.ResultBean.UserListBean> list = new ArrayList<>();

    private int pageSize = 30;
    private int pageNo = 1;
    private String userId = UserManager.getUserId();

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow_list);
        if(null != getIntent()){
            userId = getIntent().getStringExtra("personId");
            if(TextUtils.isEmpty(userId)){
                userId = UserManager.getUserId();
            }
        }
        initView();
        getData();
    }

    private void initView() {
        manager = new LinearLayoutManager(this);
        mAdapter = new MyMvvmAdapter<>(this,list,R.layout.item_user_list, BR.UserListBean);
        binding.followListRecyclerView.setLayoutManager(manager);
        binding.followListRecyclerView.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));
        binding.followListRecyclerView.setAdapter(mAdapter);

        binding.followListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1 == mAdapter.getItemCount()){
                    loadMore();
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

    private void getData(){
        loading();
        list.clear();
        pageNo = 1;
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        params.put("userId", userId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_FOLLOWLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                CommonUserListResultModel userZanListResultModel = new Gson().fromJson(result, CommonUserListResultModel.class);
                if (userZanListResultModel.isSuccess()) {
                    list.addAll(userZanListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });
    }

    private void loadMore() {
        pageNo ++;
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        params.put("userId", userId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_FOLLOWLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                CommonUserListResultModel userZanListResultModel = new Gson().fromJson(result, CommonUserListResultModel.class);
                if (userZanListResultModel.isSuccess() && userZanListResultModel.getResult().getResultList() != null && userZanListResultModel.getResult().getResultList().size() > 0) {
                    list.addAll(userZanListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }else{
                    //Toast.makeText(FollowListActivity.this, getResources().getString(R.string.no_more_2), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });
    }

    /**
     * 设置item点击事件
     * @param view
     * @param bean
     */
    @BindingAdapter({"setUserListClick"})
    public static void setUserListClick(final LinearLayout view, final CommonUserListResultModel.ResultBean.UserListBean bean){
        if(null != bean){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), PersonContentActivity.class);
                    intent.putExtra(Appconstant.PERSON_CENTER, bean.getUserId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @BindingAdapter({"setUsetListPic"})
    public static void setUsetListPic(ImageView view , CommonUserListResultModel.ResultBean.UserListBean bean){
        if(null != bean){
            Glide.with(view.getContext()).load(bean.getPhotoUrl()).transform(new GlideCircleTransform(view.getContext()))
                    .dontAnimate()
                    .placeholder(R.drawable.avater_default)
                    .into(view);
        }
    }
}
