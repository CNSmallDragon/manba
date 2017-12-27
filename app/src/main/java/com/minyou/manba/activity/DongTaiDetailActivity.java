package com.minyou.manba.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.minyou.manba.R;
import com.minyou.manba.databinding.ActivityDongtaiDetailBinding;
import com.minyou.manba.network.okhttputils.ManBaRequestManager;
import com.minyou.manba.network.okhttputils.OkHttpServiceApi;
import com.minyou.manba.network.okhttputils.ReqCallBack;
import com.minyou.manba.network.resultModel.ZoneDetailResultModel;
import com.minyou.manba.ui.view.GlideCircleTransform;
import com.minyou.manba.ui.view.MultiImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/26.
 */
public class DongTaiDetailActivity extends Activity {

    private static final String TAG = "DongTaiDetailActivity";
    private ActivityDongtaiDetailBinding binding;
    private RequestManager glideRequest;
    private String info_id = null;
    private String user_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dongtai_detail);
        //setContentView(R.layout.activity_dongtai_detail);
        glideRequest = Glide.with(this);
        Intent intent = getIntent();
        if (null != intent) {
            info_id = intent.getStringExtra("id");
            user_id = intent.getStringExtra("userID");
        }
        getDongTaiInfo(info_id);
    }


    private void getDongTaiInfo(String info_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user_id);
        ManBaRequestManager.getInstance().requestAsyn(OkHttpServiceApi.HTTP_GET_ZONE_DETAIL + "/" + info_id, ManBaRequestManager.TYPE_GET, params, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ZoneDetailResultModel zoneDetailResultModel = new Gson().fromJson(result, ZoneDetailResultModel.class);
                showUI(zoneDetailResultModel.getResult());

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        });

    }

    private void showUI(final ZoneDetailResultModel.ZoneDetailBean itemInfo) {
        binding.atvTitle.setTitle(getResources().getString(R.string.detail_title));
        // 设置右上角按钮
        binding.atvTitle.setRightToDo(R.drawable.details_icon_guanzhu, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DongTaiDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        // 设置显示
        if (TextUtils.isEmpty(itemInfo.getUserPhotoUrl())) {
            glideRequest.load(R.drawable.login_icon_qq).transform(new GlideCircleTransform(this)).into(binding.include.ivUserPic);
        } else {
            glideRequest.load(itemInfo.getUserPhotoUrl()).transform(new GlideCircleTransform(this)).into(binding.include.ivUserPic);
        }
        if (itemInfo.getSex() == 1) {
            glideRequest.load(R.drawable.home_icon_nan).into(binding.include.ivSex);
        } else {
            glideRequest.load(R.drawable.home_icon_women).into(binding.include.ivSex);
        }
        binding.include.tvUsername.setText(itemInfo.getNickName());
        // 加载发布时间
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date date = new Date(itemInfo.getPublishTime());
        binding.include.tvPubTime.setText(format.format(date));
        if (TextUtils.isEmpty(itemInfo.getGuildName())) {
            binding.include.tvFamilyname.setVisibility(View.GONE);
        } else {
            binding.include.tvFamilyname.setText(itemInfo.getGuildName());
        }
        // 关注按钮点击事件
        binding.include.tvGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.include.tvGuanzhu.setText(getResources().getString(R.string.home_guanzhu_done));
                binding.include.tvGuanzhu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                Toast.makeText(DongTaiDetailActivity.this, "已加入", Toast.LENGTH_SHORT).show();
                // TODO
            }
        });
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


}
