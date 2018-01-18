package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityShoucangBinding;
import com.minyou.manba.fragment.FavoriteFragment;

/**
 * Created by Administrator on 2017/11/28.
 */
public class ShouCangActivity extends DataBindingBaseActivity {


    private ActivityShoucangBinding binding;
    private FavoriteFragment fragment;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shoucang);
        fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showBackIcon",true);
        //binding.favoriteContent
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.favorite_content,fragment).commit();
    }


}
