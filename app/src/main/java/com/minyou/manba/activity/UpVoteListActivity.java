package com.minyou.manba.activity;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.ActivityUpvoteListBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.ui.RefreshItemDecoration;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luchunhao on 2018/1/13.
 */

public class UpVoteListActivity extends DataBindingBaseActivity {

    private static final String TAG = "UpVoteListActivity";

    private ActivityUpvoteListBinding binding;

    private List<UserZanListResultModel.UserZanListInnerBean> list = new ArrayList<UserZanListResultModel.UserZanListInnerBean>();
    private LinearLayoutManager manager;
    private MyMvvmAdapter<UserZanListResultModel.UserZanListInnerBean> mAdapter;
    private String info_id;

    private int pageSize = 20;
    private int pageNo = 1;

    @Override
    public void setContentViewAndBindData() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_upvote_list);

        if(null != getIntent()){
            info_id = getIntent().getStringExtra("info_id");
        }

        initView();

        getZanList(info_id);

    }

    private void initView() {
        manager = new LinearLayoutManager(this);
        mAdapter = new MyMvvmAdapter<>(this,list,R.layout.item_upvote_list, BR.UserZanListInnerBean);
        binding.upvoteListRecyclerView.setLayoutManager(manager);
        binding.upvoteListRecyclerView.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));
        binding.upvoteListRecyclerView.setAdapter(mAdapter);

        binding.upvoteListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1 == mAdapter.getItemCount()){
                    // TODO
                    LogUtil.d(TAG,"loadmore-------------------");
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

    /**
     * 获取点赞列表
     *
     * @param info_id
     */
    private void getZanList(String info_id) {
        list.clear();
        pageNo = 1;
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTELIST + "/" + info_id, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserZanListResultModel userZanListResultModel = new Gson().fromJson(result, UserZanListResultModel.class);
                if (userZanListResultModel.isSuccess()) {
                    List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList = userZanListResultModel.getResult();
                    list.addAll(userZanListInnerBeanList);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    private void loadMore() {
        pageNo ++;
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTELIST + "/" + info_id, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserZanListResultModel userZanListResultModel = new Gson().fromJson(result, UserZanListResultModel.class);
                if (userZanListResultModel.isSuccess()) {
                    List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList = userZanListResultModel.getResult();
                    list.addAll(userZanListInnerBeanList);
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    /**
     * 设置item点击事件
     * @param view
     * @param bean
     */
    @BindingAdapter({"setUpvoteListClick"})
    public static void setUpvoteListClick(final LinearLayout view, final UserZanListResultModel.UserZanListInnerBean bean){
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

    @BindingAdapter({"setUpvoteListUserPic"})
    public static void setUpvoteListUserPic(ImageView view ,UserZanListResultModel.UserZanListInnerBean bean){
        if(null != bean){
            Glide.with(view.getContext()).load(bean.getPhotoUrl()).transform(new GlideCircleTransform(view.getContext()))
                    .dontAnimate()
                    .placeholder(R.drawable.avater_default)
                    .into(view);
        }
    }
}
