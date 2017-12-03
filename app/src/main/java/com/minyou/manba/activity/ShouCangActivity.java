package com.minyou.manba.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.minyou.manba.R;
import com.minyou.manba.adapter.ShouCangAdapter;
import com.minyou.manba.bean.ShouCangBean;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.ui.RefreshItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/28.
 */
public class ShouCangActivity extends BaseActivity {

    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.rv_shoucang)
    RecyclerView rv_shoucang;

    private List<ShouCangBean> list = new ArrayList<>();
    private ShouCangAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shoucang;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        atv_title.setTitle(getResources().getString(R.string.my_shoucang));
        initData();
        mAdapter = new ShouCangAdapter(this,list);
        rv_shoucang.setLayoutManager(new LinearLayoutManager(this));
        rv_shoucang.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));
        rv_shoucang.setAdapter(mAdapter);
    }

    private void initData() {
        ShouCangBean bean = new ShouCangBean();
        bean.setUser_name("test");
        bean.setShouc_time(System.currentTimeMillis());
        bean.setPub_content(getResources().getString(R.string.test_more));
        list.add(bean);
    }

}
