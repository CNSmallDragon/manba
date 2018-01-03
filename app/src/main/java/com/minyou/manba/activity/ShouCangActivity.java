package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.minyou.manba.R;
import com.minyou.manba.adapter.ShouCangAdapter;
import com.minyou.manba.bean.ShouCangBean;
import com.minyou.manba.databinding.ActivityShoucangBinding;
import com.minyou.manba.ui.RefreshItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
public class ShouCangActivity extends DataBindingBaseActivity {

    private List<ShouCangBean> list = new ArrayList<>();
    private ShouCangAdapter mAdapter;

    private ActivityShoucangBinding binding;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shoucang);
        initView();
    }


    public void initView() {
        initData();
        mAdapter = new ShouCangAdapter(this,list);
        binding.rvShoucang.setLayoutManager(new LinearLayoutManager(this));
        binding.rvShoucang.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));
        binding.rvShoucang.setAdapter(mAdapter);
    }

    private void initData() {
        ShouCangBean bean = new ShouCangBean();
        bean.setUser_name("test");
        bean.setShouc_time(System.currentTimeMillis());
        bean.setPub_content(getResources().getString(R.string.test_more));
        list.add(bean);
    }


}
