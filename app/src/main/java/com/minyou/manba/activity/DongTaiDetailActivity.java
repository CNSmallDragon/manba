package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityDongtaiDetailBinding;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.fragment.NewFragment;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.network.resultModel.ZoneDetailResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */
public class DongTaiDetailActivity extends DataBindingBaseActivity implements View.OnClickListener {

    private static final String TAG = "DongTaiDetailActivity";
    private ActivityDongtaiDetailBinding binding;
    private RequestManager glideRequest;
    private String info_id = null;
    private String user_id = null;

    private int pageSize = 20;
    private int pageNo = 1;

    private ZoneDetailResultModel.ZoneDetailBean zoneDetailBean;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dongtai_detail);
        //setContentView(R.layout.activity_dongtai_detail);
        glideRequest = Glide.with(this);
        Intent intent = getIntent();
        if (null != intent) {
            info_id = intent.getStringExtra("id");
            user_id = intent.getStringExtra("userID");
        }
        // 获取动态详情
        getDongTaiInfo(info_id);
        // 获取点赞列表
        getZanList(info_id);
        // 获取评论列表
        getCommnetList(info_id,pageNo,pageSize);

        initListener();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initListener() {
        binding.tvZan.setOnClickListener(this);
        binding.tvShoucang.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvSend.setOnClickListener(this);
        binding.scrollviewDetal.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                binding.llComment.setVisibility(View.GONE);
                binding.rlMenu.setVisibility(View.VISIBLE);
                CommonUtil.closeKeybord(binding.etCommentText,binding.etCommentText.getContext());
            }
        });
    }

    /**
     * 获取评论列表
     * @param info_id
     * @param pageNo
     * @param pageSize
     */
    private void getCommnetList(String info_id, int pageNo, int pageSize) {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("zoneId",info_id);
        params.put("pageSize",String.valueOf(pageSize));
        params.put("pageNo",String.valueOf(pageNo));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    /**
     * 获取点赞列表
     * @param info_id
     */
    private void getZanList(String info_id) {
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTELIST + "/" + info_id, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserZanListResultModel userZanListResultModel = new Gson().fromJson(result,UserZanListResultModel.class);
                if(userZanListResultModel.isSuccess()){
                    List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList =  userZanListResultModel.getResult();
                    if(userZanListInnerBeanList.size() > 0 && userZanListInnerBeanList.size() < 6){
                        binding.ivMore.setVisibility(View.GONE);
                        for(UserZanListResultModel.UserZanListInnerBean bean : userZanListInnerBeanList){
                            ImageView imageView = new ImageView(binding.llZanList.getContext());
                            // 计算宽高
                            int screenWidth = CommonUtil.getScreenWidth(binding.llZanList.getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/9,screenWidth/9);
                            params.setMarginStart(CommonUtil.dip2px(binding.llZanList.getContext(),10));
                            imageView.setLayoutParams(params);
                            if(TextUtils.isEmpty(bean.getPhotoUrl())){
                                Glide.with(binding.llZanList.getContext()).load(R.drawable.register_home_pre).into(imageView);
                            }else{
                                Glide.with(binding.llZanList.getContext()).load(bean.getPhotoUrl()).transform(new GlideCircleTransform(binding.llZanList.getContext())).into(imageView);
                            }
                            binding.llZanList.addView(imageView);
                        }
                    }else if(userZanListInnerBeanList.size() > 6){
                        for(int i=0;i <= 5;i++){
                            ImageView imageView = new ImageView(binding.llZanList.getContext());
                            // 计算宽高
                            int screenWidth = CommonUtil.getScreenWidth(binding.llZanList.getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/9,screenWidth/9);
                            imageView.setLayoutParams(params);
                            if(TextUtils.isEmpty(userZanListInnerBeanList.get(i).getPhotoUrl())){
                                Glide.with(binding.llZanList.getContext()).load(R.drawable.register_home_pre).into(imageView);
                            }else{
                                Glide.with(binding.llZanList.getContext()).load(userZanListInnerBeanList.get(i).getPhotoUrl()).transform(new GlideCircleTransform(binding.llZanList.getContext())).into(imageView);
                            }
                            binding.llZanList.addView(imageView);
                        }
                        binding.ivMore.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }


    /**
     * 获取动态详情
     * @param info_id
     */
    private void getDongTaiInfo(String info_id) {
        loading();
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user_id);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_DETAIL + "/" + info_id, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneDetailResultModel zoneDetailResultModel = new Gson().fromJson(result, ZoneDetailResultModel.class);
                if(null != zoneDetailResultModel){
                    zoneDetailBean = zoneDetailResultModel.getResult();
                    binding.setZoneDetailBean(zoneDetailBean);

                }
                cancelLoading();
                showUI(zoneDetailResultModel.getResult());

            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });

    }


    private void showUI(final ZoneDetailResultModel.ZoneDetailBean itemInfo) {
        binding.atvTitle.setTitle(getResources().getString(R.string.detail_title));
        // 设置右上角按钮
        binding.atvTitle.setRightToDo(getResources().getString(R.string.detail_right), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DongTaiDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        // 设置头像显示
        NewFragment.setUserPicList(binding.include.rlUserPic,zoneDetailBean.toZoneListBean());
        binding.include.tvUsername.setText(itemInfo.getNickName());
        // 加载发布时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(itemInfo.getPublishTime());
        binding.include.tvPubTime.setText(format.format(date));
        if (TextUtils.isEmpty(itemInfo.getGuildName())) {
            binding.include.tvFamilyname.setVisibility(View.GONE);
        } else {
            binding.include.tvFamilyname.setText(itemInfo.getGuildName());
        }
        // 关注按钮点击事件
        NewFragment.setDongTaiGuanZhu(binding.include.tvGuanzhu,zoneDetailBean.toZoneListBean());
        // 点赞按钮
        //NewFragment.setDongTaiZan(binding.tvZan,zoneDetailBean.toZoneListBean());
        // 加载发布图片
        binding.include.multiImagView.setList(itemInfo.getZoneImage());
        binding.include.multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DongTaiDetailActivity.this, ImageViewerActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) itemInfo.getZoneImage());
                startActivity(intent);
            }
        });
        // 加载内容
        binding.include.tvContentDesc.setText(itemInfo.getZoneContent());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        HashMap<String,String> params;
        switch (v.getId()){
            case R.id.tv_zan:
                LogUtil.d(TAG,"v=========" + binding.tvZan.isChecked());
                if(binding.tvZan.isChecked()){// 点赞
                    zoneDetailBean.setUpvote(true);
                    zoneDetailBean.setUpvoteNum(zoneDetailBean.getUpvoteNum() + 1);
                }else{
                    zoneDetailBean.setUpvote(false);
                    zoneDetailBean.setUpvoteNum(zoneDetailBean.getUpvoteNum() - 1);
                }

                // 通知列表页更新数据
                EventInfo info = new EventInfo();
                info.setType(EventInfo.ZONE_UPDATE_ZAN);
                EventBus.getDefault().post(info);

                params = new HashMap<String, String>();
                params.put("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                params.put("zoneId",String.valueOf(zoneDetailBean.getId()));
                ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTE, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                    @Override
                    public void onReqSuccess(String result) {

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });

                break;
            case R.id.tv_shoucang:
                if(binding.tvShoucang.isChecked()){
                    zoneDetailBean.setFavorite(true);
                    zoneDetailBean.setFavoriteNum(zoneDetailBean.getFavoriteNum() + 1);
                }else {
                    zoneDetailBean.setFavorite(false);
                    zoneDetailBean.setFavoriteNum(zoneDetailBean.getFavoriteNum() - 1);
                }
                params = new HashMap<String, String>();
                params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                params.put("zoneId",String.valueOf(zoneDetailBean.getId()));
                ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_FAVORITE, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                    @Override
                    public void onReqSuccess(String result) {

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                });
                break;
            case R.id.tv_comment:
                binding.llComment.setVisibility(View.VISIBLE);
                binding.rlMenu.setVisibility(View.GONE);
                break;

            case R.id.tv_send:
                if(TextUtils.isEmpty(binding.etCommentText.getText().toString())){
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    params = new HashMap<String,String>();
                    params.put("commentParentId","");
                    params.put("userId",SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                    params.put("zoneId",zoneDetailBean.getId()+"");
                    params.put("content",binding.etCommentText.getText().toString());
                    ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENT, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                        @Override
                        public void onReqSuccess(String result) {

                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    });
                }
                break;
        }
    }
}
