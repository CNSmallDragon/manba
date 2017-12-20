package com.minyou.manba;

import android.app.Application;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.minyou.manba.imageloader.GlideImageLoader;

/**
 * Created by luchunhao on 2017/12/8.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static MyApplication mApplication;

    private int maxImgCount = 8;               //允许选择图片最大数

    public static MyApplication getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
        initImagePicker();

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
}
