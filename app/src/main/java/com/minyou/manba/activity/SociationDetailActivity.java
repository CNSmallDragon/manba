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
import android.support.v4.widget.NestedScrollView;
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
import com.minyou.manba.manager.UserManager;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.BaseResultModel;
import com.minyou.manba.network.resultModel.GuildDetailResultModel;
import com.minyou.manba.network.resultModel.GuildMembersResultModel;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.FastBlurUtil;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SociationDetailActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "SociationDetailActivity";

    private ActivitySociationDetailBinding binding;

    private GuildDetailResultModel.GuildDetailBean guildDetailBean;

    private FragmentTransaction transaction;
    private NewFragment fragment;
    private Intent intent;

    private String guildId;
    private float person_bg_height;         // 背景图片高度
    private float common_title_height;      // actionTitle高度
    int statusBarHeight;            // 状态栏高度

    private LinearLayoutManager manager;
    private MyMvvmAdapter<ZoneListResultModel.ResultBean.ZoneListBean> zoneAdapter;
    private MyMvvmAdapter<UserZanListResultModel.UserZanListInnerBean> memberAdapter;
    private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
    private List<UserZanListResultModel.UserZanListInnerBean> memberList = new ArrayList<UserZanListResultModel.UserZanListInnerBean>();
    private int pageSize = 20;
    private int pageNo = 1;
    private int memberPageNo = 1;
    private String currentUserId = UserManager.getUserId();
    private int recyclerViewType = 0;   // 当前加载的recyclerview类型，0 动态，1成员

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sociation_detail);
        intent = getIntent();
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
        binding.guildTitle.setBackground(null);
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

        zoneAdapter = new MyMvvmAdapter<>(this, zoneList, R.layout.item_home_new, BR.zoneBean);
        memberAdapter = new MyMvvmAdapter<>(this,memberList,R.layout.item_upvote_list,BR.UserZanListInnerBean);
        binding.guildRecyclerView.setLayoutManager(manager);
        binding.guildRecyclerView.setHasFixedSize(true);
        binding.guildRecyclerView.setNestedScrollingEnabled(false);
        binding.guildRecyclerView.setAdapter(zoneAdapter);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showUI() {
        // 设置模糊背景
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
        recyclerViewType = 0;
        // 加入还是退出公会
        LogUtil.d(TAG,"showUI====" + guildDetailBean.isGuildMember());
//        if(guildDetailBean.isGuildMember()){    // 是该公会成员
//            binding.tvJoin.setText(getResources().getString(R.string.gonghui_exit));
//            binding.tvJoin.setBackgroundResource(R.drawable.shape_borderline_redfull);
//        }else{
//            binding.tvJoin.setText(getResources().getString(R.string.gonghui_shenqingjiaru));
//            binding.tvJoin.setBackgroundResource(R.drawable.shape_borderline_bluefull);
//        }


        // 默认加载公会动态
//        fragment = new NewFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("sourceType","4");
//        bundle.putString("guildId",guildId);
//        fragment.setArguments(bundle);
//        transaction.replace(R.id.guild_content,fragment).commit();

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initListener() {
        binding.tvJoin.setOnClickListener(this);
        binding.llDongtaiChengyuan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.rb_dongtai){
                    recyclerViewType = 0;
                    binding.guildRecyclerView.setAdapter(zoneAdapter);
                    zoneAdapter.notifyDataSetChanged();
                }else if(checkedId == R.id.rb_member){
                    recyclerViewType = 1;
                    binding.guildRecyclerView.setAdapter(memberAdapter);
                    memberAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.guildScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    LogUtil.i(TAG, "到底了，开始加载更多...");
                    if(recyclerViewType == 0 ){
                        loadMoreZoneListData();
                        zoneAdapter.notifyDataSetChanged();
                    }else if(recyclerViewType == 1){
                        loadMoreMemberInfo();
                        memberAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    /**
     *  获取公会详情
     * @param guildId
     */
    private void getGuildDetailInfo(String guildId) {
        loading();
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GUILD_DETAIL + "/" + guildId, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
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

        // 获取成员列表
        getMemberInfo();

    }

    /**
     * 获取公会动态
     */
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
                cancelLoading();
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);
                if(zoneListResultModel.isSuccess()){
                    zoneList.clear();
                    zoneList.addAll(zoneListResultModel.getResult().getResultList());
                    zoneAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });

    }

    public void loadMoreZoneListData() {
        pageNo ++;
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("sourceType", "1");
        params.put("guildId", guildId);
        params.put("currentUserId", currentUserId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);
                if(zoneListResultModel.isSuccess() && null != zoneListResultModel.getResult().getResultList()){
                    zoneList.addAll(zoneListResultModel.getResult().getResultList());
                    zoneAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    /**
     * 获取公会成员
     */
    public void getMemberInfo() {
        memberPageNo = 1;
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(memberPageNo));
        params.put("guildId", String.valueOf(guildId));
        params.put("userId", String.valueOf(currentUserId));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GUILD_MEMBER, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                GuildMembersResultModel guildMembersResultModel = new Gson().fromJson(result,GuildMembersResultModel.class);
                if(guildMembersResultModel.isSuccess()){
                    memberList.clear();
                    for(GuildMembersResultModel.ResultBean.GuildMemberBean bean : guildMembersResultModel.getResult().getResultList()){
                        memberList.add(bean.toUserZanListInnerBean());
                    }
                    memberAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    public void loadMoreMemberInfo(){
        memberPageNo ++;
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(memberPageNo));
        params.put("guildId", String.valueOf(guildId));
        params.put("userId", String.valueOf(currentUserId));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GUILD_MEMBER, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                GuildMembersResultModel guildMembersResultModel = new Gson().fromJson(result,GuildMembersResultModel.class);
                if(guildMembersResultModel.isSuccess() && null != guildMembersResultModel.getResult().getResultList()){
                    for(GuildMembersResultModel.ResultBean.GuildMemberBean bean : guildMembersResultModel.getResult().getResultList()){
                        memberList.add(bean.toUserZanListInnerBean());
                    }
                    memberAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_join:
                LogUtil.d(TAG,"guildDetailBean.isGuildMember()====" + guildDetailBean.isGuildMember());
                if(guildDetailBean.isGuildMember()){
                    exitGuild();
                }else{
                    joinGuild();
                }
                break;
        }
    }

    /**
     * 加入公会
     */
    private void joinGuild() {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userId",currentUserId);
        params.put("guildId",guildId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GUILD_JOIN, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                BaseResultModel baseResultModel = new Gson().fromJson(result,BaseResultModel.class);
                if(baseResultModel.isSuccess()){
                    guildDetailBean.setGuildMember(true);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                guildDetailBean.setGuildMember(false);
            }
        });
    }

    /**
     * 退出公会
     */
    private void exitGuild() {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userId",currentUserId);
        params.put("guildId",guildId);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GUILD_EXIT, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                BaseResultModel baseResultModel = new Gson().fromJson(result,BaseResultModel.class);
                if(baseResultModel.isSuccess()){
                    guildDetailBean.setGuildMember(false);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }
}
