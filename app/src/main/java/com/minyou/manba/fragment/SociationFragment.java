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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.activity.SociationDetailActivity;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.FragmentGonghuiBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.SociationResultModel;
import com.minyou.manba.ui.view.DefalutRefreshView;
import com.minyou.manba.ui.view.GlideCircleTransform;
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

    private int pageSize = 100;
    private int pageNo = 1;

    private List<SociationResultModel.ResultBean.SociationResultBean> sociationList;
    private MyMvvmAdapter<SociationResultModel.ResultBean.SociationResultBean> myMvvmAdapter;
    private LinearLayoutManager layoutManager;

    private FragmentGonghuiBinding binding;
    private View footView;

    private boolean isPublic = true;
    private String httpUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gonghui, container, false);

        Bundle bundle = getArguments();
        if(null != bundle){
            isPublic = bundle.getBoolean("isPublic",true);
        }

        initView();
        getData();
        initListener();
        return binding.getRoot();
    }

    private void initListener() {
        binding.pcflRefreshSociation.setPtrHandler(new PtrHandler2() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !binding.pcflRefreshSociation.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
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

        sociationList = new ArrayList<SociationResultModel.ResultBean.SociationResultBean>();
        myMvvmAdapter = new MyMvvmAdapter<>(getActivity(), sociationList, R.layout.item_gonghui_list, BR.SociationBean);
        // 底部刷新控件
        binding.pcflRefreshSociation.setHeaderView(new DefalutRefreshView(getActivity()));
        footView = new DefalutRefreshView(getActivity());
        binding.pcflRefreshSociation.setFooterView(footView);
        layoutManager = new LinearLayoutManager(getActivity()) {

        };
        binding.rvGonghuiList.setLayoutManager(layoutManager);
        binding.rvGonghuiList.setAdapter(myMvvmAdapter);
    }

    public void getData() {
        pageNo = 1;
        sociationList.clear();
        HashMap<String, String> params = new HashMap<String, String>();
        // TODO 根据isPublic判断是获取所有工会还是只获取当前用户加入的工会
        if(isPublic){   // 查询所有工会列表
            httpUrl = OkHttpServiceApi.HTTP_GET_GUILD_LIST;
        }else{
            httpUrl = OkHttpServiceApi.HTTP_GET_USER_GUILD_LIST;
        }
        params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(httpUrl, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                SociationResultModel sociationResultModel = new Gson().fromJson(result, SociationResultModel.class);
                if (null != sociationResultModel && sociationResultModel.isSuccess()) {
                    sociationList.addAll(sociationResultModel.getResult().getResultList());
                    myMvvmAdapter.notifyDataSetChanged();
                }
                footView.setVisibility(View.GONE);
                binding.pcflRefreshSociation.refreshComplete();
            }

            @Override
            public void onReqFailed(String errorMsg) {
                binding.pcflRefreshSociation.refreshComplete();
            }
        });


    }

    /**
     * 点击进入公会详情界面
     *
     * @param view
     * @param bean
     */
    @BindingAdapter({"setOnSociationItemClick"})
    public static void setOnSociationItemClick(final View view, final SociationResultModel.ResultBean.SociationResultBean bean) {
        if (bean != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), SociationDetailActivity.class);
                    intent.putExtra(Appconstant.SOCIATION_ID, String.valueOf(bean.getGuildId()));
                    view.getContext().startActivity(intent);
                }
            });

        }
    }

    /**
     * 显示公会头像
     *
     * @param view
     * @param bean
     */
    @BindingAdapter({"setSociationPic"})
    public static void setSociationPic(final ImageView view, final SociationResultModel.ResultBean.SociationResultBean bean) {
        if (bean != null) {
            Glide.with(view.getContext()).load(bean.getGuildPhoto()).dontAnimate()
                    .placeholder(R.drawable.avater_default)
                    .transform(new GlideCircleTransform(view.getContext()))
                    .into(view);


        }
    }


    @BindingAdapter({"joinSociation"})
    public static void joinSociation(final TextView textView, final SociationResultModel.ResultBean.SociationResultBean bean) {
        if (bean != null) {
            // TODO 加入公会

        }
    }


}
