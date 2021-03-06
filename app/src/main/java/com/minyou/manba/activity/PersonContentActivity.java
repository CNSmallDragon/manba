package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.ActivityPersonContentBinding;
import com.minyou.manba.databinding.ItemHomeNewBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.PersonHomeResultModel;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.HeadZoomScrollView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */
public class PersonContentActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "PersonContentActivity";

    private ActivityPersonContentBinding binding;
    private String userId = SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID);

    private float person_bg_height;         // 背景图片高度
    private float common_title_height;      // actionTitle高度

    int statusBarHeight;            // 状态栏高度
    private RelativeLayout.LayoutParams params;
    private int personId;

    private PersonHomeResultModel.PersonResultBean resultBean = null;
    private MyMvvmAdapter<ZoneListResultModel.ResultBean.ZoneListBean> myMvvmAdapter;
    private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_person_content);

        // 设置沉浸式状态栏
        statusBarHeight = CommonUtil.getStatusBarHeight(getApplicationContext());
        params = (RelativeLayout.LayoutParams) binding.atvTitle.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        binding.atvTitle.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        person_bg_height = getResources().getDimension(R.dimen.person_bg_height);
        common_title_height = getResources().getDimension(R.dimen.common_title_height);

        if(null != getIntent()){
            personId = getIntent().getIntExtra(Appconstant.PERSON_CENTER,0);
        }

        initView();
        getPersonHomeInfo();
    }

    private void initView() {

        final ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        final View statusBarView = new View(getWindow().getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        binding.atvTitle.setBackground(null);
        initListener();

        binding.personScrollView.setOnScrollListener(new HeadZoomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= person_bg_height - statusBarHeight - common_title_height) {
                    binding.atvTitle.setBackgroundColor(Color.WHITE);
                    statusBarView.setBackgroundColor(Color.BLACK);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    // 设置标题
                    binding.atvTitle.setTitle(resultBean != null ? resultBean.getNickName() : "Manba");
                } else {
                    binding.atvTitle.setBackground(null);
                    statusBarView.setBackgroundColor(Color.TRANSPARENT);
                    decorViewGroup.removeView(statusBarView);
                    decorViewGroup.addView(statusBarView);
                    binding.atvTitle.setTitle("");
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

    private void initListener() {
        binding.tvPersonPics.setOnClickListener(this);
        binding.tvRecentDongtai.setOnClickListener(this);
        binding.tvShowAll.setOnClickListener(this);
        binding.tvFensi.setOnClickListener(this);
        binding.tvFensiCount.setOnClickListener(this);
        binding.tvGuanzhu.setOnClickListener(this);
        binding.tvGuanzhuCount.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showUI() {
        if(null == resultBean) return;
        // 显示用户头像及性别
        if(TextUtils.isEmpty(resultBean.getIconUrl())){
            Glide.with(this).load(R.drawable.register_home_pre).transform(new GlideCircleTransform(this)).into(binding.ivUserPic);
        }else{
            Glide.with(this).load(resultBean.getIconUrl()).transform(new GlideCircleTransform(this)).into(binding.ivUserPic);
        }
        if(resultBean.getSex() == 1){
            Glide.with(this).load(R.drawable.home_icon_nan).into(binding.ivSex);
        }else{
            Glide.with(this).load(R.drawable.home_icon_women).into(binding.ivSex);
        }
        // 显示背景图
        if(TextUtils.isEmpty(resultBean.getBackgroundUrl())){
            if(TextUtils.isEmpty(resultBean.getIconUrl())){
                Glide.with(this).load("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D220/sign=23ad215530f33a87816d0718f65d1018/95eef01f3a292df5166a0e70b6315c6034a8732e.jpg").into(binding.ivBgHead);
            }else{
                Glide.with(this).load(resultBean.getIconUrl()).into(binding.ivBgHead);
            }
        }else{
            Glide.with(this).load(resultBean.getBackgroundUrl()).into(binding.ivBgHead);
        }
        // 关注按钮
        // 是否是自己
        if(resultBean.getUserId() == Integer.parseInt(SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID))){
            binding.tvPersonGuanzhu.setVisibility(View.GONE);
        }else {
            binding.tvPersonGuanzhu.setVisibility(View.VISIBLE);
            if(resultBean.isFollow()){    // 已经关注
                binding.tvPersonGuanzhu.setText(getResources().getString(R.string.home_guanzhu_done));
                binding.tvPersonGuanzhu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }else{      // 未关注
                Drawable drawable= getResources().getDrawable(R.drawable.home_icon_guanzhu,null);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                binding.tvPersonGuanzhu.setText(getResources().getString(R.string.home_guanzhu));
                binding.tvPersonGuanzhu.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
            binding.tvPersonGuanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultBean.setFollow(resultBean.isFollow() ? false : true);
                    if(resultBean.isFollow()){    // 已经关注
                        binding.tvPersonGuanzhu.setText(getResources().getString(R.string.home_guanzhu_done));
                        binding.tvPersonGuanzhu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    }else{      // 未关注
                        Drawable drawable= getResources().getDrawable(R.drawable.home_icon_guanzhu,null);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        binding.tvPersonGuanzhu.setText(getResources().getString(R.string.home_guanzhu));
                        binding.tvPersonGuanzhu.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    }
                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                    params.put("followId",String.valueOf(resultBean.getUserId()));

                    ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_FOLLOW, ManBaRequestManager.TYPE_POST_JSON, params, null);
                }
            });
        }

        // 加载相册列表
        if(null != resultBean.getImageList() && resultBean.getImageList().size() > 0){
            binding.llContentPicList.removeAllViews();
            for(final String image : resultBean.getImageList()){
                ImageView imageView = new ImageView(this);
                // 计算宽高
                int screenWidth = CommonUtil.getScreenWidth(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/6,screenWidth/6);
                params.setMarginStart(CommonUtil.dip2px(this,10));
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(image).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(image);
                        Intent intent = new Intent(PersonContentActivity.this, ImageViewerActivity.class);
                        intent.putStringArrayListExtra("imageList", list);
                        startActivity(intent);
                    }
                });
                binding.llContentPicList.addView(imageView);
            }
        }

        // recycler加载最近动态
//        zoneList = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
//        zoneList.addAll(resultBean.getZoneList());
//
//        myMvvmAdapter = new MyMvvmAdapter<>(this, zoneList, R.layout.item_home_new, BR.zoneBean);
//        binding.rvPersonDongtai.setLayoutManager(new FullyLinearLayoutManager(this));
//        binding.rvPersonDongtai.setAdapter(myMvvmAdapter);

        // LinearLayout加载最近动态
        binding.llRecentDongtai.removeAllViews();
        if(null != resultBean.getZoneList()){
            for(ZoneListResultModel.ResultBean.ZoneListBean bean : resultBean.getZoneList()){
                ItemHomeNewBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.item_home_new,binding.llRecentDongtai,false);
                itemBinding.setZoneBean(bean);
                binding.llRecentDongtai.addView(itemBinding.getRoot());
            }
        }

    }

    /**
     * 从网络获取用户主页信息
     */
    public void getPersonHomeInfo() {
        loading();
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("loginUserId",userId);
        params.put("userId",String.valueOf(personId));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_USER_MYHOME, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                cancelLoading();
                PersonHomeResultModel personHomeResultModel = new Gson().fromJson(result,PersonHomeResultModel.class);
                if(personHomeResultModel.isSuccess()){
                    resultBean = personHomeResultModel.getResult();
                    binding.setPersonResultBean(resultBean);
                    showUI();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.tv_person_pics:
                // 相册
                intent = new Intent(PersonContentActivity.this,PersonGalleryActivity.class);
                intent.putExtra(Appconstant.User.USER_ID,String.valueOf(personId));
                startActivity(intent);
                break;
            case R.id.tv_show_all:
            case R.id.tv_recent_dongtai:
                intent = new Intent(PersonContentActivity.this,MyDongTaiActivity.class);
                if(!userId.equals(String.valueOf(personId))){
                    // 自己的动态
                    intent.putExtra("userId",String.valueOf(personId));
                }
                startActivity(intent);
                break;
            case R.id.tv_fensi:
            case R.id.tv_fensi_count:
                intent = new Intent(PersonContentActivity.this, FansActivity.class);
                intent.putExtra("personId",String.valueOf(personId));
                startActivity(intent);
                break;
            case R.id.tv_guanzhu:
            case R.id.tv_guanzhu_count:
                intent = new Intent(PersonContentActivity.this, FollowListActivity.class);
                intent.putExtra("personId",String.valueOf(personId));
                startActivity(intent);
                break;
        }
    }
}
