package com.minyou.manba.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.R;
import com.minyou.manba.activity.DongTaiDetailActivity;
import com.minyou.manba.adapter.NewRecyclerAdapter;
import com.minyou.manba.bean.ItemInfo;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/6.
 */
public class NewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewFragment.class.getSimpleName();
    @BindView(R.id.new_swiper)
    SwipeRefreshLayout new_swiper;
    @BindView(R.id.new_recyclerview)
    RecyclerView new_recyclerview;

    private NewRecyclerAdapter adapter;
    private List<ItemInfo> list;
    private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList;

    private int pageSize = 10;
    private int pageNo = 1;

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.fragment_new;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    public void getData() {
        pageNo = 1;
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        params.put("sourceType",String.valueOf(1));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result,ZoneListResultModel.class);
                LogUtil.d(TAG, "-----response---------" + result);
                LogUtil.d(TAG, "-----response---------" + zoneListResultModel.getResult().getResultList());
                zoneList.clear();
                zoneList.addAll(zoneListResultModel.getResult().getResultList());
                adapter.notifyDataSetChanged();
                //结束后停止刷新
                new_swiper.setRefreshing(false);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    private void loadMore(){
        pageNo ++;
        HashMap<String,String> params = new HashMap<>();
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        params.put("sourceType",String.valueOf(1));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result,ZoneListResultModel.class);

                if(zoneListResultModel.getResult().getResultList().size() > 0){
                    zoneList.addAll(zoneListResultModel.getResult().getResultList());
                    adapter.notifyDataSetChanged();
                    //结束后停止刷新
                    new_swiper.setRefreshing(false);
                }else{
                    adapter.changeMoreStatus(NewRecyclerAdapter.NO_LOAD_MORE);
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<ItemInfo>();
        zoneList = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
        new_swiper.setOnRefreshListener(this);
        new_swiper.setColorSchemeColors(Color.BLUE);
        new_swiper.setRefreshing(true);
        getData();

        new_recyclerview.setItemAnimator(new DefaultItemAnimator());
        //new_recyclerview.addItemDecoration(new RefreshItemDecoration(getActivity(), RefreshItemDecoration.VERTICAL_LIST));
        new_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewRecyclerAdapter(getActivity(), zoneList);
        new_recyclerview.setAdapter(adapter);

        initListener();


    }

    private void initListener() {

        new_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()){
                    adapter.changeMoreStatus(NewRecyclerAdapter.LOADING_MORE);
                    loadMore();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });

        adapter.setOnItemClick(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                ZoneListResultModel.ResultBean.ZoneListBean info = zoneList.get(position);
                Intent intent = new Intent(getActivity(), DongTaiDetailActivity.class);
                intent.putExtra("id",info.getId()+"");    // 动态id
                startActivity(intent);
            }
        });

        new_recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getActivity()).resumeRequests();
                }else{
                    Glide.with(getActivity()).pauseRequests();
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
