package com.minyou.manba.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.minyou.manba.R;

/**
 * Created by luchunhao on 2018/1/21.
 */

public class GlideUtil {

    // http://res.mymanba.cn/Fmr7r1GbYH8vNa_mMlYxJIyE5x2W?imageView2/3/h/400/w/300

    public final static int LIST_IMG_HEIGHT = 400;
    public final static int LIST_IMG_WIDTH = 300;


    /**
     * 加载原图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadPrimaryImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(R.drawable.default_w)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_w)
                .into(imageView);
    }

    public static void loadListImage(Context context, String url, ImageView imageView) {
        String listUrl = url + "?imageView2/3/h/" + LIST_IMG_HEIGHT + "/w/" + LIST_IMG_WIDTH;
        Glide.with(context).load(listUrl)
                .placeholder(R.drawable.default_w)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_w)
                .into(imageView);
    }

    public static void loadDefaultImage(Context context, String url, ImageView imageView){
        String defaultUrl = url + "?imageslim";
        Glide.with(context).load(defaultUrl)
                .placeholder(R.drawable.default_w)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_w)
                .into(imageView);
    }
}
