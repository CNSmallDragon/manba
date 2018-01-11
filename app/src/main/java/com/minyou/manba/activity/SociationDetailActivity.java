package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.ActivitySociationDetailBinding;
import com.minyou.manba.fragment.NewFragment;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.GuildDetailResultModel;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.FastBlurUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SociationDetailActivity extends DataBindingBaseActivity {

    private static final String TAG = "SociationDetailActivity";

    private ActivitySociationDetailBinding binding;

    private GuildDetailResultModel.GuildDetailBean guildDetailBean;

    private FragmentTransaction transaction;
    private NewFragment fragment;

    private String guildId;
    private float person_bg_height;         // 背景图片高度
    private float common_title_height;      // actionTitle高度
    int statusBarHeight;            // 状态栏高度

    private LinearLayoutManager manager;
    private MyMvvmAdapter<ZoneListResultModel.ResultBean.ZoneListBean> zoneAdapter;
    private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
    private int pageSize = 20;
    private int pageNo = 1;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sociation_detail);
        Intent intent = getIntent();
        if (null != intent) {
            guildId = intent.getStringExtra(Appconstant.SOCIATION_ID);
        }

        // 设置沉浸式状态栏
        statusBarHeight = CommonUtil.getStatusBarHeight(getApplicationContext());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.guildTitle.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        binding.guildTitle.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        transaction = getSupportFragmentManager().beginTransaction();
        manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);

        getGuildDetailInfo(guildId);
        initView();
        initListener();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void initView() {

        final ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        final View statusBarView = new View(getWindow().getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);

        person_bg_height = getResources().getDimension(R.dimen.sociation_head_height);
        common_title_height = getResources().getDimension(R.dimen.common_title_height);

        binding.guildScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= person_bg_height - statusBarHeight - common_title_height) {
                    binding.guildTitle.setBackgroundColor(Color.WHITE);
                    statusBarView.setBackgroundColor(Color.BLACK);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    // 设置标题
                    binding.guildTitle.setTitle(guildDetailBean != null ? guildDetailBean.getGuildName() : "Manba");
                } else {
                    binding.guildTitle.setBackground(null);
                    statusBarView.setBackgroundColor(Color.TRANSPARENT);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    binding.guildTitle.setTitle("");
                    //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //透明状态栏
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        //透明导航栏
                        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    }
                }
            }
        });

    }

    private void showUI() {
        // 设置模糊背景
//        final Bitmap[] scaledBitmap = {null};
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    scaledBitmap[0] = Glide.with(SociationDetailActivity.this).load(guildDetailBean.getGuildPhoto()).asBitmap().centerCrop().into(500, 500).get();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap[0], 100);
//                            Drawable drawable = new BitmapDrawable(blurBitmap);
//                            binding.rlGonghui.setBackground(drawable);
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        Observable.create(new Observable.OnSubscribe<Bitmap>(){

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap scaledBitmap = null;
                try {
                    scaledBitmap = Glide.with(SociationDetailActivity.this).load(guildDetailBean.getGuildPhoto()).asBitmap().centerCrop().into(500, 500).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                subscriber.onNext(scaledBitmap);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 100);
                        Drawable drawable = new BitmapDrawable(blurBitmap);
                        binding.rlGonghui.setBackground(drawable);
                    }
                });

        // 显示头像
        Glide.with(this).load(guildDetailBean.getGuildPhoto()).dontAnimate()
                .placeholder(R.drawable.avater_default)
                .transform(new GlideCircleTransform(this))
                .into(binding.ivGuildPic);

        // 设置动态默认选中
        binding.llDongtaiChengyuan.check(R.id.rb_dongtai);

        // 如果是从个人中心进入则设置地步按钮为退出公会
//        if (Appconstant.MINE2GONGHUI.equals(intent.getStringExtra(Appconstant.FROM_WHERE))) {
//            tv_join.setText(getResources().getString(R.string.gonghui_exit));
//            tv_join.setBackgroundColor(Color.RED);
//        }

        // 默认加载公会动态
//        fragment = new NewFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("sourceType","4");
//        bundle.putString("guildId",guildId);
//        fragment.setArguments(bundle);
//        transaction.replace(R.id.guild_content,fragment).commit();

        zoneAdapter = new MyMvvmAdapter<>(this, zoneList, R.layout.item_home_new, BR.zoneBean);
        binding.guildRecyclerView.setLayoutManager(manager);
        binding.guildRecyclerView.setHasFixedSize(true);
        binding.guildRecyclerView.setNestedScrollingEnabled(false);
        binding.guildRecyclerView.setAdapter(zoneAdapter);

    }

    private void initListener() {
        binding.llDongtaiChengyuan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if(checkedId == R.id.rb_dongtai){
//                    LogUtil.d(TAG,"=================动态");
//                    if(null != fragment){
//                        LogUtil.d(TAG,"=================动态show");
//                        transaction.show(fragment);
//                    }
//                }else if(checkedId == R.id.rb_member){
//                    if(null != fragment){
//                        transaction.hide(fragment);
//                    }
//                    LogUtil.d(TAG,"=================成员");
//                }
            }
        });
    }

    private void getGuildDetailInfo(String guildId) {
        // 获取公会详情
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GUILD_DETAIL + "/" + guildId, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                GuildDetailResultModel guildDetailResultModel = new Gson().fromJson(result, GuildDetailResultModel.class);
                if (guildDetailResultModel.isSuccess()) {
                    guildDetailBean = guildDetailResultModel.getResult();
                    binding.setGuildDetailBean(guildDetailBean);
                    showUI();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

        // 获取公会动态
        getZoneListData();

    }

    public void getZoneListData() {
        pageNo = 1;
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("sourceType", "1");
        params.put("guildId", guildId);
        params.put("currentUserId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);
                if(zoneListResultModel.isSuccess()){
                    zoneList.clear();
                    zoneList.addAll(zoneListResultModel.getResult().getResultList());
                    zoneAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

}
