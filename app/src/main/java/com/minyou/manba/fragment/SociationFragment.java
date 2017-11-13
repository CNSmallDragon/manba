package com.minyou.manba.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.activity.SociationDetailActivity;
import com.minyou.manba.adapter.SociationListAdapter;
import com.minyou.manba.adapter.SociationRecyclerAdapter;
import com.minyou.manba.bean.SociationBean;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.ui.RefreshItemDecoration;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class SociationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "SociationFragment";
    public static final int DETAIL = 0;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwiper;
    @BindView(R.id.rv_gonghui_list)
    RecyclerView mRecyclerView;
    private ListView lv_gonghui_list;
    private SociationListAdapter adapter;
    private SociationRecyclerAdapter mRecyclerAdapter;
    private List<SociationBean> list;
    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindString(R.string.home_gonghui)
    String home_gonghui;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_gonghui;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        atv_title.setTitle(home_gonghui);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<SociationBean>();

        mSwiper.setOnRefreshListener(this);
        mSwiper.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwiper.setProgressBackgroundColorSchemeColor(Color.parseColor("#BBFFFF"));
        mSwiper.setRefreshing(true);
        getData();

        // mRecyclerView加载数据
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RefreshItemDecoration(getActivity(), RefreshItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerAdapter = new SociationRecyclerAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        // 设置条目点击事件
        mRecyclerAdapter.setOnItemClickListener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), SociationDetailActivity.class);
                intent.putExtra(Appconstant.SOCIATION_NAME, list.get(position).getName());
                intent.putExtra(Appconstant.SOCIATION_MEMBER_NUM, "成员数：" + list.get(position).getMemberNum());
                intent.putExtra(Appconstant.SOCIATION_HOT_NUM, "活跃度" + list.get(position).getMemberNum());
                intent.putExtra(Appconstant.SOCIATION_PIC_PATH, list.get(position).getPicPath());
                getActivity().startActivityForResult(intent, DETAIL);
            }
        });

        // 设置滑动监听，上拉加载更多
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.d(TAG,"mRecyclerAdapter.getItemCount()=========" + mRecyclerAdapter.getItemCount());
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem +1 == mRecyclerAdapter.getItemCount()){
                    mRecyclerAdapter.changeMoreStatus(mRecyclerAdapter.LOADING_MORE);
                    // 模拟网络获取数据
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 1; i <= 20; i++) {
                                SociationBean e = new SociationBean();
                                e.setName("test公会" + i);
                                e.setMemberNum((int) (Math.random() * 10000));
                                e.setHotNum((int) (Math.random() * 10000));
                                list.add(e);
                            }
                            //adapter.notifyDataSetChanged();
                            mRecyclerAdapter.notifyDataSetChanged();
                            mRecyclerAdapter.changeMoreStatus(mRecyclerAdapter.PULLUP_LOAD_MORE);
                        }
                    }, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = manager.findLastVisibleItemPosition();
                LogUtil.d(TAG,"lastVisibleItem=========" + lastVisibleItem);
            }
        });


        /*
        adapter = new SociationListAdapter(getActivity(), list);
        lv_gonghui_list.setAdapter(adapter);
        lv_gonghui_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SociationDetailActivity.class);
                intent.putExtra(Appconstant.SOCIATION_NAME, list.get(position).getName());
                intent.putExtra(Appconstant.SOCIATION_MEMBER_NUM, "成员数：" + list.get(position).getMemberNum());
                intent.putExtra(Appconstant.SOCIATION_HOT_NUM, "活跃度" + list.get(position).getMemberNum());
                intent.putExtra(Appconstant.SOCIATION_PIC_PATH, list.get(position).getPicPath());
                getActivity().startActivityForResult(intent, DETAIL);

            }
        });
        */
        // 设置添加公会按钮事件
        atv_title.setRightToDo(R.drawable.guild_nav_icon_add, null, new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub  添加公会

            }
        });
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i <= 20; i++) {
                    SociationBean e = new SociationBean();
                    e.setName("test公会" + i);
                    e.setMemberNum((int) (Math.random() * 10000));
                    e.setHotNum((int) (Math.random() * 10000));
                    list.add(e);
                }
                //adapter.notifyDataSetChanged();
                mRecyclerAdapter.notifyDataSetChanged();
                //结束后停止刷新
                mSwiper.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 1; i <= 20; i++) {
                    SociationBean e = new SociationBean();
                    e.setName("test公会" + i);
                    e.setMemberNum((int) (Math.random() * 10000));
                    e.setHotNum((int) (Math.random() * 10000));
                    list.add(e);
                }
                //adapter.notifyDataSetChanged();
                mRecyclerAdapter.notifyDataSetChanged();
                //结束后停止刷新
                mSwiper.setRefreshing(false);
            }
        }, 3000);


    }
}
