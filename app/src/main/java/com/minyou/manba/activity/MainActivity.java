package com.minyou.manba.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.minyou.manba.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends Activity implements OnClickListener {

    private String[] ads = {"http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109135437541.png",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20170725104352981.jpg",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109145155437.gif",
            "http://chatm-icon.oss-cn-beijing.aliyuncs.com/pic/pic_20171109153125234.jpg"};

    private static final long WAIT_HOME = 3000;
    private Unbinder unbinder;
    @BindView(R.id.iv_welcome)
    ImageView iv_welcome;

    private String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        // 计算屏幕宽高
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        Random random = new Random();
        int nextInt = random.nextInt(10)%4;

        switch (nextInt){
            case 0:
                picUrl = ads[0];
                break;
            case 1:
                picUrl = ads[1];
                break;
            case 2:
                picUrl = ads[2];
                break;
            case 3:
                picUrl = ads[3];
                break;
        }

        Glide.with(this)
                .load(picUrl)
                .override(width,height)
                .into(iv_welcome);

        // 延时3s进入主界面
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(WAIT_HOME);
                    goHome();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
    }

    public void goHome(){
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
