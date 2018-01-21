package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;

import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityFeedbackBinding;

/**
 * Created by luchunhao on 2018/1/20.
 */

public class FeedbackActivity extends DataBindingBaseActivity {

    private ActivityFeedbackBinding binding;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
    }
}
