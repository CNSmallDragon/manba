package com.minyou.manba.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.minyou.manba.R;
import com.minyou.manba.adapter.ImageViewerAdapter;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/26.
 */
public class ImageViewerActivity extends Activity {

    private Unbinder unbinder;

    @BindView(R.id.tv_view)
    TextView tv_view;
    @BindView(R.id.image_menu)
    ImageView image_menu;
    @BindView(R.id.viewpager_image)
    ViewPager viewpager_image;

    private ImageViewerAdapter mAdapter;
    private List<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        unbinder = ButterKnife.bind(this);

        imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.test_01);
//        imageList.add(R.drawable.test_02);
//        imageList.add(R.drawable.test_03);
//        imageList.add(R.drawable.test_04);
//        imageList.add(R.drawable.test_05);
//        imageList.add(R.drawable.test_06);

        mAdapter = new ImageViewerAdapter(this,imageList);
        viewpager_image.setAdapter(mAdapter);
        // 初始化数目
        tv_view.setText(1 + "/" + (imageList.size()));
        viewpager_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.d("===onPageScrolled===","position========" + position);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("==onPageSelected====","position========" + position);
                tv_view.setText(position+1 + "/" + (imageList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewpager_image.setCurrentItem(3);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
