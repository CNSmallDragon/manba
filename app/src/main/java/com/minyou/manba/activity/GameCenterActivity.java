package com.minyou.manba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.R;
import com.minyou.manba.adapter.GameTopAdapter;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/2.
 */
public class GameCenterActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.game_viewpager)
    ViewPager game_viewpager;
    @BindView(R.id.ll_game1)
    LinearLayout ll_game1;
    @BindView(R.id.ll_game2)
    LinearLayout ll_game2;
    @BindView(R.id.ll_game3)
    LinearLayout ll_game3;
    @BindView(R.id.iv_game_pic1)
    ImageView iv_game_pic1;
    @BindView(R.id.tv_game_name1)
    TextView tv_game_name1;
    @BindView(R.id.tv_game_desc1)
    TextView tv_game_desc1;
    @BindView(R.id.iv_game_pic2)
    ImageView iv_game_pic2;
    @BindView(R.id.tv_game_name2)
    TextView tv_game_name2;
    @BindView(R.id.tv_game_desc2)
    TextView tv_game_desc2;
    @BindView(R.id.iv_game_pic3)
    ImageView iv_game_pic3;
    @BindView(R.id.tv_game_name3)
    TextView tv_game_name3;
    @BindView(R.id.tv_game_desc3)
    TextView tv_game_desc3;
    @BindView(R.id.rv_game_jingpin)
    RecyclerView rv_game_jingpin;
    @BindView(R.id.ll_dot)
    LinearLayout ll_dot;

    private List<Integer> tmp_topPicPaths = new ArrayList<Integer>();
//    private List<String> topPicPaths = new ArrayList<String>();
    private GameTopAdapter topAdapter;
    private RequestManager requestManager;

    private static final int VIEW_PAGE_AUTIO = 100;
    private static final String TAG = "GameCenterActivity";

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VIEW_PAGE_AUTIO:
                    if(null != game_viewpager && null != topAdapter){
                        LogUtil.d(TAG,"game_viewpager.getCurrentItem()===" + game_viewpager.getCurrentItem());
                        LogUtil.d(TAG,"tmp_topPicPaths.size()===" + tmp_topPicPaths.size());
                        game_viewpager.setCurrentItem(game_viewpager.getCurrentItem() + 1);
                    }
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_gamecenter;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        requestManager = Glide.with(this);
        atv_title.setTitle(getResources().getString(R.string.game_title));
        game_viewpager.addOnPageChangeListener(this);
        tmpData();
        topAdapter = new GameTopAdapter(this,tmp_topPicPaths);
        game_viewpager.setAdapter(topAdapter);
        int firstItem = Integer.MAX_VALUE /2 - Integer.MAX_VALUE /2%tmp_topPicPaths.size();
        game_viewpager.setCurrentItem(firstItem);
        // 开启轮播图
        Message message = Message.obtain();
        message.what = VIEW_PAGE_AUTIO;
        handler.sendMessageDelayed(message,3000);
        
        initListener();
    }

    private void initListener() {
    }

    private void tmpData() {
        tmp_topPicPaths.add(R.drawable.test_01);
        tmp_topPicPaths.add(R.drawable.test_02);
        tmp_topPicPaths.add(R.drawable.test_03);
        tmp_topPicPaths.add(R.drawable.test_04);
        tmp_topPicPaths.add(R.drawable.test_05);
        tmp_topPicPaths.add(R.drawable.test_06);
        // 初始化viewpager下方dot
        ll_dot.removeAllViews();
        for(int i=0;i<tmp_topPicPaths.size();i++){
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtil.dip2px(this,10),CommonUtil.dip2px(this,10));
            params.setMargins(CommonUtil.dip2px(this,5),0,0,0);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(R.drawable.select_dot);
            ll_dot.addView(imageView);
        }
        // 默认选中第一个
        ll_dot.getChildAt(0).setSelected(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<tmp_topPicPaths.size();i++){
            if(i == position%tmp_topPicPaths.size()){
                ll_dot.getChildAt(i).setSelected(true);
            }else{
                ll_dot.getChildAt(i).setSelected(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case ViewPager.SCROLL_STATE_DRAGGING:
                // 开始滑动状态
                handler.removeCallbacksAndMessages(null);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                // 滑动完成状态
                Message message = Message.obtain();
                message.what = VIEW_PAGE_AUTIO;
                handler.sendMessageDelayed(message,3000);
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                // 手指离开屏幕状态
                break;
        }
    }


    /**
     * 点击今日推荐游戏进入详情页
     * @param view
     */
    @OnClick({R.id.ll_game1,R.id.ll_game2,R.id.ll_game3})
    public void toGameDetail(LinearLayout view){
        Intent intent = new Intent(this,GameDetailActivity.class);
        switch (view.getId()){
            case R.id.ll_game1:
                startActivity(intent);
                break;
            case R.id.ll_game2:

                break;
            case R.id.ll_game3:

                break;
        }
    }
}
