package com.minyou.manba.fragment;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.activity.SociationDetailActivity;
import com.minyou.manba.adapter.SociationListAdapter;
import com.minyou.manba.adapter.SociationRecyclerAdapter;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.FragmentGonghuiBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.SociationResultModel;
import com.minyou.manba.ui.view.DefalutRefreshView;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

public class SociationFragment extends DataBindingBaseFragment {
    public static final String TAG = "SociationFragment";
    public static final int DETAIL = 0;

    private int pageSize = 20;
    private int pageNo = 1;

    private ListView lv_gonghui_list;
    private SociationListAdapter adapter;
    private SociationRecyclerAdapter mRecyclerAdapter;
    private List<SociationResultModel.ResultBean.SociationResultBean> sociationList;
    private MyMvvmAdapter<SociationResultModel.ResultBean.SociationResultBean> myMvvmAdapter;
    private LinearLayoutManager layoutManager;

    private FragmentGonghuiBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gonghui, container, false);
        initView();
        getData();
        initListener();
        return binding.getRoot();
    }

    private void initListener() {
        binding.pcflRefreshSociation.setPtrHandler(new PtrHandler2() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                LogUtil.d(TAG, "checkCanDoRefresh===" + (!binding.pcflRefreshSociation.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header)));
                return !binding.pcflRefreshSociation.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                LogUtil.d(TAG, "checkCanDoLoadMore===" + (!binding.pcflRefreshSociation.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, footer)));
                return !binding.pcflRefreshSociation.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                loadMore();
            }
        });
    }

    private void loadMore() {
    }


    public void initView() {
        binding.atvTitle.setTitle(getResources().getString(R.string.home_gonghui));
        binding.atvTitle.hideBackIcon();

        sociationList = new ArrayList<SociationResultModel.ResultBean.SociationResultBean>();
        myMvvmAdapter = new MyMvvmAdapter<>(getActivity(), sociationList, R.layout.item_gonghui_list, BR.SociationBean);
        // 底部刷新控件
        binding.pcflRefreshSociation.setHeaderView(new DefalutRefreshView(getActivity()));
        binding.pcflRefreshSociation.setFooterView(new DefalutRefreshView(getActivity()));
        layoutManager = new LinearLayoutManager(getActivity()) {

        };
        binding.rvGonghuiList.setLayoutManager(layoutManager);
        binding.rvGonghuiList.setAdapter(myMvvmAdapter);
    }

    public void getData() {
        pageNo = 1;
        sociationList.clear();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_GUILD_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                SociationResultModel sociationResultModel = new Gson().fromJson(result, SociationResultModel.class);
                if (null != sociationResultModel && sociationResultModel.isSuccess()) {
                    sociationList.addAll(sociationResultModel.getResult().getResultList());
                    myMvvmAdapter.notifyDataSetChanged();
                }
                binding.pcflRefreshSociation.refreshComplete();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                binding.pcflRefreshSociation.refreshComplete();
            }
        });


    }

    @BindingAdapter({"setOnSociationItemClick"})
    public static void setOnSociationItemClick(final View view, final SociationResultModel.ResultBean.SociationResultBean bean) {
        if (bean != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), SociationDetailActivity.class);
                    intent.putExtra(Appconstant.SOCIATION_NAME, bean.getGuildName());
                    intent.putExtra(Appconstant.SOCIATION_MEMBER_NUM, "成员数：" + bean.getMemberNum());
                    intent.putExtra(Appconstant.SOCIATION_HOT_NUM, "活跃度" + bean.getLiveness());
                    intent.putExtra(Appconstant.SOCIATION_PIC_PATH, bean.getGuildPhoto());
                    view.getContext().startActivity(intent);
                }
            });

        }
    }


}
