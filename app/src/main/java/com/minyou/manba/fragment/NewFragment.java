package com.minyou.manba.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.BR;
import com.minyou.manba.R;
import com.minyou.manba.activity.DongTaiDetailActivity;
import com.minyou.manba.activity.ImageViewerActivity;
import com.minyou.manba.activity.PersonContentActivity;
import com.minyou.manba.adapter.mvvm.MyMvvmAdapter;
import com.minyou.manba.databinding.FragmentNewBinding;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.ZoneListResultModel;
import com.minyou.manba.ui.view.DefalutRefreshView;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Created by Administrator on 2017/11/6.
 */
public class NewFragment extends DataBindingBaseFragment {

    private static final String TAG = NewFragment.class.getSimpleName();

    private FragmentNewBinding binding;

    private MyMvvmAdapter<ZoneListResultModel.ResultBean.ZoneListBean> myMvvmAdapter;
    private List<ZoneListResultModel.ResultBean.ZoneListBean> zoneList;

    private int pageSize = 10;
    private int pageNo = 1;
    private String sourceType = "1";
    private String userId;

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new, container, false);
        EventBus.getDefault().register(this);

        Bundle bundle = getArguments();
        if(null != bundle){
            sourceType = bundle.getString("sourceType","1");
            userId = bundle.getString("userId","");
        }
        initView();

        return binding.getRoot();
    }

    private void initView() {
        zoneList = new ArrayList<ZoneListResultModel.ResultBean.ZoneListBean>();
        getData();

        myMvvmAdapter = new MyMvvmAdapter<>(getActivity(), zoneList, R.layout.item_home_new, BR.zoneBean);
        // 底部刷新控件
        binding.pcflRefreshNew.setHeaderView(new DefalutRefreshView(getActivity()));
        binding.pcflRefreshNew.setFooterView(new DefalutRefreshView(getActivity()));
        binding.newRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRecyclerview.setAdapter(myMvvmAdapter);

        initListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onZanReturn(EventInfo info) {
        if(null != info && info.getType() == EventInfo.ZONE_UPDATE_ZAN){
            LogUtil.d(TAG,"notifyDataSetChanged=================");
            myMvvmAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //getData();
    }

    public void getData() {
        pageNo = 1;
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("sourceType", sourceType);
        if("4".equals(sourceType)){
            params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        }else if("5".equals(sourceType)){
            params.put("userId", userId);
        }
        params.put("currentUserId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);
                if(zoneListResultModel.isSuccess() && zoneListResultModel.getResult().getTotalCount() > 0){
                    binding.pcflRefreshNew.setVisibility(View.VISIBLE);
                    binding.tvEmptyText.setVisibility(View.GONE);
                    zoneList.clear();
                    zoneList.addAll(zoneListResultModel.getResult().getResultList());
                    myMvvmAdapter.notifyDataSetChanged();
                }else{
                    binding.pcflRefreshNew.setVisibility(View.GONE);
                    binding.tvEmptyText.setVisibility(View.VISIBLE);
                }
                binding.pcflRefreshNew.refreshComplete();
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    private void loadMore() {
        pageNo++;
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("sourceType", sourceType);
        if("4".equals(sourceType)){
            params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        }else if("5".equals(sourceType)){
            params.put("userId", userId);
        }
        params.put("currentUserId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_LIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneListResultModel zoneListResultModel = new Gson().fromJson(result, ZoneListResultModel.class);

                if (null != zoneListResultModel.getResult().getResultList() && zoneListResultModel.getResult().getResultList().size() > 0) {
                    zoneList.addAll(zoneListResultModel.getResult().getResultList());
                    myMvvmAdapter.notifyDataSetChanged();
                    //结束后停止刷新
                    binding.pcflRefreshNew.refreshComplete();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_more_2), Toast.LENGTH_SHORT).show();
                    binding.pcflRefreshNew.refreshComplete();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    private void initListener() {

        binding.pcflRefreshNew.setPtrHandler(new PtrHandler2() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !binding.pcflRefreshNew.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return !binding.pcflRefreshNew.isRefreshing() && PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                loadMore();
            }
        });


        binding.newRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).resumeRequests();
                } else {
                    Glide.with(getActivity()).pauseRequests();
                }

            }
        });


    }

    /**
     * 设置列表页用户头像
     *
     * @param view
     * @param zoneBean
     */
    @BindingAdapter({"setUserPicList"})
    public static void setUserPicList(final RelativeLayout view, final ZoneListResultModel.ResultBean.ZoneListBean zoneBean) {
        if (zoneBean != null) {
            ImageView userPic = (ImageView) view.findViewById(R.id.iv_user_pic);
            ImageView userSex = (ImageView) view.findViewById(R.id.iv_sex);
            if(TextUtils.isEmpty(zoneBean.getUserPhotoUrl())){
                Glide.with(view.getContext()).load(R.drawable.register_home_pre).transform(new GlideCircleTransform(view.getContext())).into(userSex);
            }else{
                Glide.with(view.getContext()).load(zoneBean.getUserPhotoUrl()).transform(new GlideCircleTransform(view.getContext())).dontAnimate()
                        .placeholder(R.drawable.avater_default).into(userPic);
            }
            if (zoneBean.getSex() == 1) {
                Glide.with(view.getContext()).load(R.drawable.home_icon_nan).transform(new GlideCircleTransform(view.getContext())).into(userSex);
            } else if (zoneBean.getSex() == 2) {
                Glide.with(view.getContext()).load(R.drawable.home_icon_women).transform(new GlideCircleTransform(view.getContext())).into(userSex);
            }
            userPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), PersonContentActivity.class);
                    intent.putExtra(Appconstant.PERSON_CENTER, zoneBean.getUserId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    /**
     * 显示发布图片列表
     *
     * @param multiImageView
     * @param zoneBean
     */
    @BindingAdapter({"setListMultiImageView"})
    public static void setListMultiImageView(final MultiImageView multiImageView, final ZoneListResultModel.ResultBean.ZoneListBean zoneBean) {
        if (zoneBean != null) {
            if (zoneBean.getZoneImage() != null && zoneBean.getZoneImage().size() > 0) {
                multiImageView.setVisibility(View.VISIBLE);
                multiImageView.setList(zoneBean.getZoneImage());
                multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(multiImageView.getContext(), ImageViewerActivity.class);
                        intent.putExtra("position", position);
                        intent.putStringArrayListExtra("imageList", (ArrayList<String>) zoneBean.getZoneImage());
                        multiImageView.getContext().startActivity(intent);
                    }
                });
            } else {
                multiImageView.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 条目点击事件
     * @param view
     * @param zoneBean
     */
    @BindingAdapter({"setOnLayoutItemClick"})
    public static void setOnLayoutItemClick(final View view, final ZoneListResultModel.ResultBean.ZoneListBean zoneBean) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), DongTaiDetailActivity.class);
                intent.putExtra("id", zoneBean.getId() + "");    // 动态id
                //intent.putExtra("userID", zoneBean.getUserId() + "");    // 用户id
                intent.putExtra("userID", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));    // 用户id
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * 关注按钮点击事件
     * @param textView
     * @param zoneBean
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @BindingAdapter({"setDongTaiGuanZhu"})
    public static void setDongTaiGuanZhu(final TextView textView,final ZoneListResultModel.ResultBean.ZoneListBean zoneBean){
        if(zoneBean != null){
            // 是否是自己发布动态
            LogUtil.d(TAG,"zoneBean=========" + zoneBean.isFollow());
            if(zoneBean.getUserId() == Integer.parseInt(SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID))){
                textView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.VISIBLE);
                if(zoneBean.isFollow()){    // 已经关注
                    textView.setText(textView.getContext().getResources().getString(R.string.home_guanzhu_done));
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }else{      // 未关注
                    Drawable drawable= textView.getContext().getResources().getDrawable(R.drawable.home_icon_guanzhu,null);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    textView.setText(textView.getContext().getResources().getString(R.string.home_guanzhu));
                    textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        zoneBean.setFollow(zoneBean.isFollow() ? false : true);
                        if(zoneBean.isFollow()){    // 已经关注
                            textView.setText(textView.getContext().getResources().getString(R.string.home_guanzhu_done));
                            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        }else{      // 未关注
                            Drawable drawable= textView.getContext().getResources().getDrawable(R.drawable.home_icon_guanzhu,null);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            textView.setText(textView.getContext().getResources().getString(R.string.home_guanzhu));
                            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        }
                        HashMap<String,String> params = new HashMap<String, String>();
                        params.put("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                        params.put("followId",String.valueOf(zoneBean.getUserId()));

                        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_FOLLOW, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {
                            @Override
                            public void onReqSuccess(String result) {

                            }

                            @Override
                            public void onReqFailed(String errorMsg) {

                            }
                        });

                    }
                });
            }
        }
    }

    /**
     * 点赞按钮
     * @param checkBox
     * @param zoneBean
     */
    @BindingAdapter({"setDongTaiZan"})
    public static void setDongTaiZan(final CheckBox checkBox, final ZoneListResultModel.ResultBean.ZoneListBean zoneBean){
        if(null != zoneBean){
            zoneBean.setUpvoteNum(zoneBean.getUpvoteNum());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){// 点赞
                        zoneBean.setUpvote(true);
                        zoneBean.setUpvoteNum(zoneBean.getUpvoteNum() + 1);
                    }else{
                        zoneBean.setUpvote(false);
                        zoneBean.setUpvoteNum(zoneBean.getUpvoteNum() - 1);

                    }

                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                    params.put("zoneId",String.valueOf(zoneBean.getId()));
                    ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTE, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                        @Override
                        public void onReqSuccess(String result) {

                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }
}
