package com.minyou.manba.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.minyou.manba.R;
import com.minyou.manba.adapter.ImageViewerAdapter;
import com.minyou.manba.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/26.
 */
public class ImageViewerActivity extends Activity {

    private Unbinder unbinder;

    @BindView(R.id.tv_view)
    TextView tv_view;
    @BindView(R.id.image_menu)
    ImageView image_menu;
    @BindView(R.id.viewpager_image)
    ViewPager viewpager_image;

    private ImageViewerAdapter mAdapter;
    private List<Integer> imageList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_viewer);
        unbinder = ButterKnife.bind(this);

        imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.test_01);
        imageList.add(R.drawable.test_02);
        imageList.add(R.drawable.test_03);
        imageList.add(R.drawable.test_04);
        imageList.add(R.drawable.test_05);
        imageList.add(R.drawable.test_06);

        image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialogMenu();
                showPupWindowMenu(view);
            }
        });

        mAdapter = new ImageViewerAdapter(this,imageList);
        viewpager_image.setAdapter(mAdapter);
        // 初始化数目
        tv_view.setText(1 + "/" + (imageList.size()));
        viewpager_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.d("===onPageScrolled===","position========" + position);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("==onPageSelected====","position========" + position);
                tv_view.setText(position+1 + "/" + (imageList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewpager_image.setCurrentItem(3);

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
                popupWindow.dismiss();
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
