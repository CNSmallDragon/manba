package com.minyou.manba.util;

import android.content.Context;
import android.content.Intent;

import java.util.Map;

/**
 * 该工具类是为了跳转activity之间使用
 *
 *
 */
public class UIHelper {

    /**
     * 启动activity 带参数
     *
     * @param context
     * @param cla
     * @param params
     */
    public static void startActicity(Context context, Class cla, Map<String, Object> params){
        Intent intent = getIntent(context, cla, params);
        context.startActivity(intent);
    }

    /**
     * 获取intent
     *
     * @param cla
     * @param params
     * @return
     */
    private static Intent getIntent(Context context, Class cla, Map<String, Object> params) {
        Intent intent = new Intent(context, cla);;
        if (params != null) {
            // TODO: 2017/11/13  按照map的key value  封装
//            intent.putExtra(key, valu
//            )
        }

        return intent;
    }







}
