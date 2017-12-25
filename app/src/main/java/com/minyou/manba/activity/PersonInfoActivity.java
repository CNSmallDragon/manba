package com.minyou.manba.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityPersonInfoBinding;

/**
 * Created by luchunhao on 2017/12/25.
 */
public class PersonInfoActivity extends AppCompatActivity {

    private ActivityPersonInfoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_info);
    }
}
