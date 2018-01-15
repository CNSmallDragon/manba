package com.minyou.manba.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.minyou.manba.R;
import com.minyou.manba.adapter.ImageViewerAdapter;
import com.minyou.manba.databinding.ActivityImageViewerBinding;
import com.minyou.manba.util.SDFileHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/26.
 */
public class ImageViewerActivity extends Activity {

    private ActivityImageViewerBinding binding;

    private static final int MY_PERMISSION_REQUEST_CODE = 1000;

    private ImageViewerAdapter mAdapter;
    private View view;
    private TextView tv_send;
    private TextView tv_save;
    private TextView tv_cancel;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    private PopupWindow popupWindow;
    private ArrayList<String> images;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_image_viewer);

        Intent intent = getIntent();
        if(null == intent){
            finish();
        }

        currentPosition = intent.getIntExtra("position",0);
        images = intent.getStringArrayListExtra("imageList");

        // 点击顶部菜单
        binding.imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialogMenu();
                showPupWindowMenu(view);
            }
        });


        mAdapter = new ImageViewerAdapter(this,images);
        binding.viewpagerImage.setAdapter(mAdapter);
        // 初始化数目
        binding.tvView.setText(1 + "/" + (images.size()));
        binding.viewpagerImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                binding.tvView.setText(position+1 + "/" + (images.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.viewpagerImage.setCurrentItem(currentPosition);

        // 长按item
        mAdapter.setOnItemLongClick(new ImageViewerAdapter.OnItemLongClickListener() {
            @Override
            public void setOnItemLongClickListener(View view, int position) {
                currentPosition = position;
                showPupWindowMenu(view);
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.activity_alpha_out);
            }
        });

    }

    public void showPupWindowMenu(View v){
        popupWindow = new PopupWindow(this);
        popupWindow.setAnimationStyle(R.style.window_anim_style);
        view = LinearLayout.inflate(this,R.layout.view_image_menu,null);
        tv_send = view.findViewById(R.id.tv_send);
        tv_save = view.findViewById(R.id.tv_save);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT); // -2 表示包裹内容 -1表示填充父窗体
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        setBackgroundAlpha(0.5f);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 分享给好友
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 保存本地
                getPermission();
                popupWindow.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void  getPermission(){
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );

        if(isAllGranted){
            SDFileHelper.getInstance().savePicture(images.get(currentPosition));
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 设置背景透明度
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void showDialogMenu(){
        builder = new AlertDialog.Builder(ImageViewerActivity.this,R.style.style_dialog);
        view = LinearLayout.inflate(this,R.layout.view_image_menu,null);
        tv_send = view.findViewById(R.id.tv_send);
        tv_save = view.findViewById(R.id.tv_save);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        //params.width = CommonUtil.getScreenWidth(getApplicationContext());

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                SDFileHelper.getInstance().savePicture(images.get(currentPosition));

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                Toast.makeText(this, "需要授权,请到 “应用信息 -> 权限” 中授予！", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
