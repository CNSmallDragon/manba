package com.minyou.manba.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
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
import com.minyou.manba.activity.detail.CommentDetailActivity;
import com.minyou.manba.adapter.CommentListAdapter;
import com.minyou.manba.databinding.ActivityDongtaiDetailBinding;
import com.minyou.manba.event.EventInfo;
import com.minyou.manba.fragment.NewFragment;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.CommentListResultModel;
import com.minyou.manba.network.resultModel.UserZanListResultModel;
import com.minyou.manba.network.resultModel.ZoneDetailResultModel;
import com.minyou.manba.ui.dialog.SelectSortTypeDialog;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.DateFormatUtil;
import com.minyou.manba.util.LogUtil;
import com.minyou.manba.util.OnItemClickLitener;
import com.minyou.manba.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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

    private int pageSize = 10;
    private int pageNo = 1;
    private int sortType = 0;   // 排序方式0表示正序，1表示倒序，2点赞最多

    private ZoneDetailResultModel.ZoneDetailBean zoneDetailBean;
    private LinearLayoutManager manager;
    private CommentListAdapter mAdapter;
    private List<CommentListResultModel.ResultBean.CommentItemBean> commentItemBeanList;

    @Override
    public void setContentViewAndBindData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dongtai_detail);
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
        commentItemBeanList = new ArrayList<CommentListResultModel.ResultBean.CommentItemBean>();
        getCommnetList(info_id);
        showCommentUI();
        initListener();
    }

    private void showCommentUI() {

        mAdapter = new CommentListAdapter(this, commentItemBeanList);
        manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        binding.recyclerComment.setLayoutManager(manager);
        binding.recyclerComment.setHasFixedSize(true);
        binding.recyclerComment.setNestedScrollingEnabled(false);
        binding.recyclerComment.setAdapter(mAdapter);
    }

    /**
     * 加载顶部数据
     */
    private void initHeadView(final ZoneDetailResultModel.ZoneDetailBean itemInfo) {
        // 设置头像显示
        NewFragment.setUserPicList(binding.includeDongtaiDetail.rlUserPic,itemInfo.toZoneListBean());
        binding.includeDongtaiDetail.tvUsername.setText(itemInfo.getNickName());
        // 加载发布时间
        binding.includeDongtaiDetail.tvPubTime.setText(DateFormatUtil.format(itemInfo.getPublishTime()));
        if (TextUtils.isEmpty(itemInfo.getGuildName())) {
            binding.includeDongtaiDetail.tvFamilyname.setVisibility(View.GONE);
        } else {
            binding.includeDongtaiDetail.tvFamilyname.setText(itemInfo.getGuildName());
        }
        // 关注按钮点击事件
        NewFragment.setDongTaiGuanZhu(binding.includeDongtaiDetail.tvGuanzhu,itemInfo.toZoneListBean());
        // 显示点赞数量
        binding.includeDongtaiDetail.tvCountZan.setText(String.valueOf(itemInfo.getUpvoteNum()));
        // 加载发布图片
        if(null != itemInfo.getZoneImage() && itemInfo.getZoneImage().size() > 0){
            binding.includeDongtaiDetail.multiImagView.setList(itemInfo.getZoneImage());
        }
        binding.includeDongtaiDetail.multiImagView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DongTaiDetailActivity.this, ImageViewerActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) itemInfo.getZoneImage());
                startActivity(intent);
            }
        });
        // 加载内容
        binding.includeDongtaiDetail.tvContentDesc.setText(itemInfo.getZoneContent());

        // 默认选中回复
//                headViewHolder.headBinding.rgCommentGroup.check(R.id.rb_comment_normal);
//                headViewHolder.headBinding.rgCommentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                        EventInfo eventInfo = new EventInfo();
//                        if(checkedId == R.id.rb_comment_normal){
//                            eventInfo.setType(COMMENT_TYPE_NORMAL);
//                            EventBus.getDefault().post(eventInfo);
//                        }else if(checkedId == R.id.rb_comment_hot){
//                            eventInfo.setType(COMMENT_TYPE_HOT);
//                            EventBus.getDefault().post(eventInfo);
//                        }
//                    }
//                });

    }

    /**
     * 记载点赞列表
     * @param userZanListInnerBeanList
     */
    private void initZanListView(final List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList) {
        // 获取点赞列表
        if(null != userZanListInnerBeanList){
            binding.includeDongtaiDetail.llZanList.removeAllViews();
            if(userZanListInnerBeanList.size() > 0 && userZanListInnerBeanList.size() < 6){
                binding.includeDongtaiDetail.ivMore.setVisibility(View.GONE);
                for(final UserZanListResultModel.UserZanListInnerBean bean : userZanListInnerBeanList){
                    ImageView imageView = new ImageView(binding.includeDongtaiDetail.llZanList.getContext());
                    // 计算宽高
                    int screenWidth = CommonUtil.getScreenWidth(binding.includeDongtaiDetail.llZanList.getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/9,screenWidth/9);
                    params.setMarginStart(CommonUtil.dip2px(binding.includeDongtaiDetail.llZanList.getContext(),10));
                    imageView.setLayoutParams(params);
                    if(TextUtils.isEmpty(bean.getPhotoUrl())){
                        Glide.with(binding.includeDongtaiDetail.llZanList.getContext()).load(R.drawable.register_home_pre).into(imageView);
                    }else{
                        Glide.with(binding.includeDongtaiDetail.llZanList.getContext()).load(bean.getPhotoUrl()).transform(new GlideCircleTransform(binding.includeDongtaiDetail.llZanList.getContext())).into(imageView);
                    }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DongTaiDetailActivity.this, PersonContentActivity.class);
                            intent.putExtra(Appconstant.PERSON_CENTER, bean.getUserId());
                            startActivity(intent);
                        }
                    });
                    binding.includeDongtaiDetail.llZanList.addView(imageView);
                }
            }else if(userZanListInnerBeanList.size() > 6){
                for(int i=0;i <= 5;i++){
                    final int userId = userZanListInnerBeanList.get(i).getUserId();
                    ImageView imageView = new ImageView(binding.includeDongtaiDetail.llZanList.getContext());
                    // 计算宽高
                    int screenWidth = CommonUtil.getScreenWidth(binding.includeDongtaiDetail.llZanList.getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/9,screenWidth/9);
                    imageView.setLayoutParams(params);
                    if(TextUtils.isEmpty(userZanListInnerBeanList.get(i).getPhotoUrl())){
                        Glide.with(binding.includeDongtaiDetail.llZanList.getContext()).load(R.drawable.register_home_pre).into(imageView);
                    }else{
                        Glide.with(binding.includeDongtaiDetail.llZanList.getContext()).load(userZanListInnerBeanList.get(i).getPhotoUrl()).transform(new GlideCircleTransform(binding.includeDongtaiDetail.llZanList.getContext())).into(imageView);
                    }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DongTaiDetailActivity.this, PersonContentActivity.class);
                            intent.putExtra(Appconstant.PERSON_CENTER, userId);
                            startActivity(intent);
                        }
                    });
                    binding.includeDongtaiDetail.llZanList.addView(imageView);
                }
                binding.includeDongtaiDetail.ivMore.setVisibility(View.VISIBLE);
            }else{
                binding.includeDongtaiDetail.ivMore.setVisibility(View.GONE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initListener() {
        // 设置右上角按钮
//        binding.atvTitle.setRightToDo(getResources().getString(R.string.detail_right), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DongTaiDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.tvZan.setOnClickListener(this);
        binding.tvShoucang.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvSend.setOnClickListener(this);
        binding.includeDongtaiDetail.llZanList.setOnClickListener(this);        // 查看点赞列表
        binding.scrollviewDetail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    LogUtil.i(TAG, "到底了，开始加载更多...");
                    loadMoreComment();
                }
            }
        });

        // 点击评论条目
        mAdapter.setOnItemClickedListener(new OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                LogUtil.d(TAG, "id=====" + commentItemBeanList.get(position).getCommentId());
                Intent intent = new Intent(DongTaiDetailActivity.this, CommentDetailActivity.class);
                intent.putExtra("commentItem", commentItemBeanList.get(position));
                startActivity(intent);
            }
        });

        /**
         * 评论排序
         */
        binding.includeDongtaiDetail.tvSortType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示选择对话框
                final SelectSortTypeDialog dialog = new SelectSortTypeDialog(DongTaiDetailActivity.this);
                dialog.show();
                dialog.setSortType(sortType);
                dialog.setDaoSelected(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 倒叙排列
                        if (sortType != 1) {
                            sortType = 1;
                            binding.includeDongtaiDetail.tvSortType.setText(getResources().getString(R.string.comment_sort_dao));
                            getCommnetList(info_id);
                        }
                        dialog.dismiss();
                    }
                });

                dialog.setZhengSelected(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sortType != 0) {
                            sortType = 0;
                            binding.includeDongtaiDetail.tvSortType.setText(getResources().getString(R.string.comment_sort_zheng));
                            getCommnetList(info_id);
                        }
                        dialog.dismiss();
                    }
                });

                dialog.setHotSelected(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sortType != 2) {
                            sortType = 2;
                            binding.includeDongtaiDetail.tvSortType.setText(getResources().getString(R.string.comment_sort_hot));
                            getCommnetList(info_id);
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    /**
     * 获取更多评论
     */
    private void loadMoreComment() {
        pageNo++;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("zoneId", info_id);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("sortType", String.valueOf(sortType));
        params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_COMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                CommentListResultModel commentListResultModel = new Gson().fromJson(result, CommentListResultModel.class);
                if (commentListResultModel.isSuccess() && null != commentListResultModel.getResult().getResultList()) {
                    commentItemBeanList.addAll(commentListResultModel.getResult().getResultList());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
            }
        });
    }

    /**
     * 获取评论列表
     *
     * @param info_id
     */
    private void getCommnetList(String info_id) {
        pageNo = 1;
        commentItemBeanList.clear();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("zoneId", info_id);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("sortType", String.valueOf(sortType));
        params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_COMMENTLIST, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                CommentListResultModel commentListResultModel = new Gson().fromJson(result, CommentListResultModel.class);
                if (commentListResultModel.isSuccess() && commentListResultModel.getResult().getResultList().size() > 0) {
                    // 显示评论数量
                    binding.includeDongtaiDetail.allReplyNumTv.setText(String.format(getResources().getString(R.string.comment_number),commentListResultModel.getResult().getTotalCount()+""));
                    commentItemBeanList.addAll(commentListResultModel.getResult().getResultList());
                    //showCommentUI();
                    mAdapter.notifyDataSetChanged();
                    binding.tvEmpty.setVisibility(View.GONE);
                } else {
                    // 显示评论数量
                    binding.includeDongtaiDetail.allReplyNumTv.setText(String.format(getResources().getString(R.string.comment_number),"0"));
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }

    /**
     * 获取点赞列表
     *
     * @param info_id
     */
    private void getZanList(String info_id) {
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_UPVOTELIST + "/" + info_id, ManBaRequestManager.TYPE_GET, null, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UserZanListResultModel userZanListResultModel = new Gson().fromJson(result, UserZanListResultModel.class);
                if (userZanListResultModel.isSuccess()) {
                    List<UserZanListResultModel.UserZanListInnerBean> userZanListInnerBeanList = userZanListResultModel.getResult();
                    initZanListView(userZanListInnerBeanList);
                    //mAdapter.setZanList(userZanListInnerBeanList);
                    //mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });
    }




    /**
     * 获取动态详情
     *
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
                if (null != zoneDetailResultModel) {
                    zoneDetailBean = zoneDetailResultModel.getResult();
                    initHeadView(zoneDetailResultModel.getResult());
                    binding.setZoneDetailBean(zoneDetailBean);
                }
                cancelLoading();
                if (mAdapter != null) {
                    mAdapter.setZoneDetailBean(zoneDetailResultModel.getResult());
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                cancelLoading();
            }
        });

    }


    /**
     * 选择排序方式
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectSortType(EventInfo info) {
        if(null != info && info.getType() == EventInfo.COMMENT_TYPE_NORMAL){
            sortType = 0;
            getCommnetList(info_id);
        }else if(null != info && info.getType() == EventInfo.COMMENT_TYPE_HOT){
            sortType = 2;
            getCommnetList(info_id);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        HashMap<String, String> params;
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_zan:
                if (binding.tvZan.isChecked()) {// 点赞
                    zoneDetailBean.setUpvote(true);
                    zoneDetailBean.setUpvoteNum(zoneDetailBean.getUpvoteNum() + 1);
                } else {
                    zoneDetailBean.setUpvote(false);
                    zoneDetailBean.setUpvoteNum(zoneDetailBean.getUpvoteNum() - 1);
                }

                // 通知列表页更新数据
                EventInfo info = new EventInfo();
                info.setType(EventInfo.ZONE_UPDATE_ZAN);
                EventBus.getDefault().post(info);

                params = new HashMap<String, String>();
                params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                params.put("zoneId", String.valueOf(zoneDetailBean.getId()));
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
                if (binding.tvShoucang.isChecked()) {
                    zoneDetailBean.setFavorite(true);
                    zoneDetailBean.setFavoriteNum(zoneDetailBean.getFavoriteNum() + 1);
                } else {
                    zoneDetailBean.setFavorite(false);
                    zoneDetailBean.setFavoriteNum(zoneDetailBean.getFavoriteNum() - 1);
                }
                params = new HashMap<String, String>();
                params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                params.put("zoneId", String.valueOf(zoneDetailBean.getId()));
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
                binding.etCommentText.setText("");
                binding.rlMenu.setVisibility(View.GONE);
                break;

            case R.id.tv_send:
                loading();
                if (TextUtils.isEmpty(binding.etCommentText.getText().toString())) {
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    params = new HashMap<String, String>();
                    params.put("userId", SharedPreferencesUtil.getInstance().getSP(Appconstant.User.USER_ID));
                    params.put("zoneId", zoneDetailBean.getId() + "");
                    params.put("content", binding.etCommentText.getText().toString());
                    ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_POST_ZONE_COMMENT, ManBaRequestManager.TYPE_POST_JSON, params, new ReqCallBack<String>() {

                        @Override
                        public void onReqSuccess(String result) {
                            cancelLoading();
                            binding.llComment.setVisibility(View.GONE);
                            binding.rlMenu.setVisibility(View.VISIBLE);
                            // TODO 更新评论列表数据
                            getCommnetList(info_id);
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {
                            cancelLoading();
                        }
                    });
                }
                break;

            case R.id.ll_zan_list:
                intent = new Intent(DongTaiDetailActivity.this,UpVoteListActivity.class);
                intent.putExtra("info_id",info_id);
                startActivity(intent);
                break;
        }
    }
}
