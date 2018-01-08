package com.minyou.manba.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.minyou.manba.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by luchunhao on 2017/12/20.
 */
public class SDFileHelper {

    private static SDFileHelper mInstance;

    private Context context = MyApplication.getInstance();

    public static SDFileHelper getInstance() {

        if (mInstance == null) {
            synchronized (SDFileHelper.class) {
                if (mInstance == null) {
                    mInstance = new SDFileHelper();
                }
            }
        }
        return mInstance;
    }


    private SDFileHelper() {
    }

    //Glide保存图片
    public void savePicture(String url) {
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaFileToSD(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //往SD卡写入文件的方法
    private void savaFileToSD(byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/Manba";
            //String filePath = context.getCacheDir().getPath();
            try {
                File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filename;
                StringBuilder sb = new StringBuilder();
                Calendar selectedDate = Calendar.getInstance();//系统当前时间
                sb.append(selectedDate.get(Calendar.YEAR));
                if((selectedDate.get(Calendar.MONTH)+1) < 10){
                    sb.append("0"+(selectedDate.get(Calendar.MONTH)+1));
                }else{
                    sb.append(selectedDate.get(Calendar.MONTH)+1);
                }
                if(selectedDate.get(Calendar.DAY_OF_MONTH) < 10){
                    sb.append("0"+selectedDate.get(Calendar.DAY_OF_MONTH));
                }else{
                    sb.append(selectedDate.get(Calendar.DAY_OF_MONTH));
                }
                sb.append(selectedDate.get(Calendar.HOUR_OF_DAY));
                if(selectedDate.get(Calendar.MINUTE) < 10){
                    sb.append("0"+selectedDate.get(Calendar.MINUTE));
                }else{
                    sb.append(selectedDate.get(Calendar.MINUTE));
                }
                sb.append(selectedDate.get(Calendar.SECOND));
                sb.append(".jpg");
                filename = filePath + "/" + sb.toString();
                //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                File file = new File(filename);
                file.createNewFile();
                if (!file.isFile()) {
                    return;
                }
                FileOutputStream output = new FileOutputStream(file);
                output.write(bytes);
                //将bytes写入到输出流中
                output.close();
                //关闭输出流
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "图片已成功保存到" + filePath, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }




}
