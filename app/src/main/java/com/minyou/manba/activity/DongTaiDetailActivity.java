package com.minyou.manba.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.bean.ItemInfo;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/26.
 */
public class DongTaiDetailActivity extends Activity {

    private static final String TAG = "DongTaiDetailActivity";
    private Unbinder unbinder;
    private ItemInfo itemInfo = null;

    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.iv_user_pic)
    ImageView iv_user_pic;
    @BindView(R.id.iv_sex)
    ImageView iv_sex;
    @BindView(R.id.iv_content_pic)
    ImageView iv_content_pic;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_pub_time)
    TextView tv_pub_time;
    @BindView(R.id.tv_familyname)
    TextView tv_familyname;
    @BindView(R.id.tv_guanzhu)
    TextView tv_guanzhu;
    @BindView(R.id.tv_content_desc)
    TextView tv_content_desc;
    @BindView(R.id.tv_count_zan)
    TextView tv_count_zan;
    @BindView(R.id.tv_shoucang)
    TextView tv_shoucang;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.ll_count_list)
    LinearLayout ll_count_list;
    @BindView(R.id.recycler_comment)
    RecyclerView recycler_comment;
    @BindView(R.id.recycler_zan)
    RecyclerView recycler_zan;

    private RequestManager glideRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongtai_detail);
        unbinder = ButterKnife.bind(this);
        glideRequest = Glide.with(this);
        Intent intent = getIntent();
        if (null != intent) {
            itemInfo = intent.getParcelableExtra(Appconstant.ITEM_INFO);
        }
        initView();
    }

    private void initView() {
        atv_title.setTitle(getResources().getString(R.string.detail_title));
        // 设置右上角按钮
        atv_title.setRightToDo(R.drawable.details_icon_guanzhu, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DongTaiDetailActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        // 设置显示
        if (TextUtils.isEmpty(itemInfo.getPhotoUrl())) {
            glideRequest.load(R.drawable.login_icon_qq).transform(new GlideCircleTransform(this)).into(iv_user_pic);
        } else {
            glideRequest.load(itemInfo.getPhotoUrl()).transform(new GlideCircleTransform(this)).into(iv_user_pic);
        }
        if (itemInfo.getSex() == 1) {
            glideRequest.load(R.drawable.home_icon_nan).into(iv_sex);
        } else {
            glideRequest.load(R.drawable.home_icon_women).into(iv_sex);
        }
        tv_username.setText(itemInfo.getUserName());
        // 加载发布时间
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date date = new Date(itemInfo.getDate());
        tv_pub_time.setText(format.format(date));
        tv_familyname.setText(itemInfo.getFamilyName());
        // 关注按钮点击事件
        tv_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtil.isLogin(getApplicationContext())) {
                    // 已经登陆
                    tv_guanzhu.setText(getResources().getString(R.string.home_guanzhu_done));
                    tv_guanzhu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    Toast.makeText(DongTaiDetailActivity.this, "已加入", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(DongTaiDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        // 加载发布图片
        if (TextUtils.isEmpty(itemInfo.getPhotoUrl())) {
            glideRequest.load(R.drawable.test_home_item).into(iv_content_pic);
        } else {
            glideRequest.load(itemInfo.getPhotoUrl()).into(iv_content_pic);
        }
        // 加载内容
        tv_content_desc.setText(itemInfo.getContentStr());
        ll_count_list.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
