package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.ActivityMyGuildListBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.SociationResultModel;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luchunhao on 2018/1/16.
 */

public class MyGuildListActivity extends DataBindingBaseActivity {

    private ActivityMyGuildListBinding binding;

    private int pageSize = 100;
    private int pageNo = 1;

    private List<SociationResultModel.ResultBean.SociationResultBean> sociationList;
    private MyMvvmAdapter<SociationResultModel.ResultBean.SociationResultBean> myMvvmAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_guild_list);

        initView();
        getData();
        initEvent();
    }

    private void initView() {
        sociationList = new ArrayList<SociationResultModel.ResultBean.SociationResultBean>();
        myMvvmAdapter = new MyMvvmAdapter<>(this, sociationList, R.layout.item_gonghui_list, BR.SociationBean);
        // 底部刷新控件
        layoutManager = new LinearLayoutManager(this) {

        };
        binding.myGuildRecyclerView.setLayoutManager(layoutManager);
        binding.myGuildRecyclerView.setAdapter(myMvvmAdapter);
    }

    public void getData() {
        pageNo = 1;
        sociationList.clear();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_USER_GUILD_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                SociationResultModel sociationResultModel = new Gson().fromJson(result, SociationResultModel.class);
                if (null != sociationResultModel && sociationResultModel.isSuccess()) {
                    sociationList.addAll(sociationResultModel.getResult().getResultList());
                    myMvvmAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
            }
        });

    }

    private void initEvent() {
    }
}
