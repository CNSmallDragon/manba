package com.minyou.manba.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.minyou.manba.R;
import com.minyou.manba.adapter.WalletAdapter;
import com.minyou.manba.bean.WalletItem;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.ui.RefreshItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/28.
 */
public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.rv_wallet)
    RecyclerView rv_wallet;

    private List<WalletItem> list;
    private WalletAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        atv_title.setTitle(getResources().getString(R.string.my_qianbao));
        if(null == list){
            list = new ArrayList<WalletItem>();
            WalletItem item1 = new WalletItem();
            item1.setType(0);
            item1.setItemName("灵气石");
            item1.setCount(10000);
            WalletItem item2 = new WalletItem();
            item2.setType(1);
            item2.setItemName("灵石");
            item2.setCount(10000);
            list.add(item1);
            list.add(item2);
        }
        if(null == adapter){
            adapter = new WalletAdapter(this,list);
        }

        rv_wallet.setLayoutManager(new LinearLayoutManager(this));
        rv_wallet.addItemDecoration(new RefreshItemDecoration(this,RefreshItemDecoration.VERTICAL_LIST));
        rv_wallet.setAdapter(adapter);
    }
}
