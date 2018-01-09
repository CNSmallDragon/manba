package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;

import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityMyDongtaiBinding;
import com.minyou.manba.fragment.NewFragment;

/**
 * Created by luchunhao on 2018/1/9.
 */

public class MyDongTaiActivity extends DataBindingBaseActivity {

    private ActivityMyDongtaiBinding binding;

    private NewFragment newFragment;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_dongtai);

        newFragment = new NewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,newFragment).commit();
    }
}
