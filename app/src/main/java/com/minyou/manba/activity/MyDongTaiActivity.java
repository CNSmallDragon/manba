package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityMyDongtaiBinding;
import com.minyou.manba.fragment.NewFragment;

/**
 * Created by luchunhao on 2018/1/9.
 */

public class MyDongTaiActivity extends DataBindingBaseActivity {

    private ActivityMyDongtaiBinding binding;

    private NewFragment newFragment;
    private String currentUserId;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_dongtai);

        if(null != getIntent()){
            currentUserId = getIntent().getStringExtra("userId");
        }

        newFragment = new NewFragment();
        Bundle bundle = new Bundle();
        if(TextUtils.isEmpty(currentUserId)){
            // 自己的动态
            bundle.putString("sourceType","4");
        }else{
            bundle.putString("sourceType","5");
            bundle.putString("userId",currentUserId);
        }
        newFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,newFragment).commit();
    }
}
