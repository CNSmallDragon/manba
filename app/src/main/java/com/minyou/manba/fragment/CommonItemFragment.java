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

import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.DongTaiDetailActivity;
import com.minyou.manba.adapter.NewRecyclerAdapter;
import com.minyou.manba.bean.ItemInfo;
import com.minyou.manba.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/28.
 */
public class CommonItemFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.new_swiper)
    SwipeRefreshLayout new_swiper;
    @BindView(R.id.new_recyclerview)
    RecyclerView new_recyclerview;

    private NewRecyclerAdapter adapter;
    private List<ItemInfo> list;

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i <= 10; i++) {
                    ItemInfo info = new ItemInfo();
                    info.setUserName("小漫吧" + i);
                    info.setDate(System.currentTimeMillis());
                    info.setFamilyName("漫吧家族");
                    info.setContentStr("今天是个好日子");
                    info.setSharedCount((int) (Math.random() * 100));
                    info.setZanCount((int) (Math.random() * 100));
                    info.setCommentCount((int) (Math.random() * 100));
                    list.add(info);
                }
                adapter.notifyDataSetChanged();
                //结束后停止刷新
                new_swiper.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<ItemInfo>();
        new_swiper.setOnRefreshListener(this);
        new_swiper.setColorSchemeColors(Color.BLUE);
        new_swiper.setRefreshing(true);
        getData();

        new_recyclerview.setItemAnimator(new DefaultItemAnimator());
        //new_recyclerview.addItemDecoration(new RefreshItemDecoration(getActivity(), RefreshItemDecoration.VERTICAL_LIST));
        new_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewRecyclerAdapter(getActivity(), list);
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 1; i <= 10; i++) {
                                ItemInfo info = new ItemInfo();
                                info.setUserName("小漫吧" + i);
                                info.setDate(System.currentTimeMillis());
                                info.setFamilyName("漫吧家族");
                                info.setContentStr("今天是个好日子");
                                info.setSharedCount((int) (Math.random() * 100));
                                info.setZanCount((int) (Math.random() * 100));
                                info.setCommentCount((int) (Math.random() * 100));
                                list.add(info);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.changeMoreStatus(NewRecyclerAdapter.PULLUP_LOAD_MORE);
                            //结束后停止刷新
                            new_swiper.setRefreshing(false);
                        }
                    }, 3000);

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
                ItemInfo info = list.get(position);
                Intent intent = new Intent(getActivity(), DongTaiDetailActivity.class);
                intent.putExtra(Appconstant.ITEM_INFO,info);
                startActivity(intent);
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
