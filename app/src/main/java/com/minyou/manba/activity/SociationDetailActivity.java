package com.minyou.manba.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.minyou.manba.Appconstant;
import com.minyou.manba.R;
import com.minyou.manba.ui.ActionTitleView;
import com.minyou.manba.util.CommonUtil;
import com.minyou.manba.util.FastBlurUtil;
import com.minyou.manba.util.GlideCircleTransform;

import butterknife.BindView;

public class SociationDetailActivity extends BaseActivity {

    @BindView(R.id.atv_title)
    ActionTitleView atv_title;
    @BindView(R.id.iv_gonghui_pic)
    ImageView iv_gonghui_pic;
    @BindView(R.id.tv_gonghui_name)
    TextView tv_gonghui_name;
    @BindView(R.id.tv_gonghui_member_num)
    TextView tv_gonghui_member_num;
    @BindView(R.id.tv_gonghui_hot_num)
    TextView tv_gonghui_hot_num;
    @BindView(R.id.tv_join)
    TextView tv_join;
    @BindView(R.id.rl_gonghui)
    RelativeLayout rl_gonghui;
    @BindView(R.id.rb_dongtai)
    RadioButton rb_dongtai;
    @BindView(R.id.rb_member)
    RadioButton rb_member;
    @BindView(R.id.ll_dongtai_chengyuan)
    RadioGroup ll_dongtai_chengyuan;

    private String pic_path;
    private RequestManager glideRequest;


    @Override
    public int getLayoutId() {
        return R.layout.activity_sociation_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (null != intent) {
            int statusBarHeight = CommonUtil.getStatusBarHeight(getApplicationContext());
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) atv_title.getLayoutParams();
            params.setMargins(0, statusBarHeight, 0, 0);
            atv_title.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
                //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            pic_path = intent.getStringExtra(Appconstant.SOCIATION_PIC_PATH);
            glideRequest = Glide.with(this);
            glideRequest.load(R.drawable.test_welcome).transform(new GlideCircleTransform(this)).into(iv_gonghui_pic);

            tv_gonghui_name.setText(intent.getStringExtra(Appconstant.SOCIATION_NAME));
            tv_gonghui_member_num.setText(intent.getStringExtra(Appconstant.SOCIATION_MEMBER_NUM));
            tv_gonghui_hot_num.setText(intent.getStringExtra(Appconstant.SOCIATION_HOT_NUM));
            // 设置模糊背景
            Bitmap scaledBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test_welcome);
            Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 100);
            Drawable drawable = new BitmapDrawable(blurBitmap);
            rl_gonghui.setBackground(drawable);

            // 设置动态默认选中
            ll_dongtai_chengyuan.check(R.id.rb_dongtai);

            // 如果是从个人中心进入则设置地步按钮为退出公会
            if(Appconstant.MINE2GONGHUI.equals(intent.getStringExtra(Appconstant.FROM_WHERE))){
                tv_join.setText(getResources().getString(R.string.gonghui_exit));
                tv_join.setBackgroundColor(Color.RED);
            }
        }

        tv_join.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
