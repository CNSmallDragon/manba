package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityPersonContentBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.PersonHomeResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.HeadZoomScrollView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import java.util.HashMap;

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
    }

    /**
     * 从网络获取用户主页信息
     */
    public void getPersonHomeInfo() {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("loginUserId",userId);
        params.put("userId",String.valueOf(personId));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_USER_MYHOME, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                PersonHomeResultModel personHomeResultModel = new Gson().fromJson(result,PersonHomeResultModel.class);
                if(personHomeResultModel.isSuccess()){
                    resultBean = personHomeResultModel.getResult();
                    binding.setPersonResultBean(resultBean);
                    showUI();
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

        }
    }
}
