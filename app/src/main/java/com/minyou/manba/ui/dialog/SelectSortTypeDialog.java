package com.minyou.manba.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.minyou.manba.R;

/**
 * Created by Administrator on 2018/1/5.
 */

public class SelectSortTypeDialog extends Dialog {

    private TextView zheng;
    private TextView dao;
    private TextView hot;
    private Context context;

    public SelectSortTypeDialog(@NonNull Context context) {
        this(context,0);
    }

    public SelectSortTypeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_sort_type);

        zheng = (TextView) findViewById(R.id.tv_sort_zheng);
        dao = (TextView) findViewById(R.id.tv_sort_dao);
        hot = (TextView) findViewById(R.id.tv_sort_hot);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setSortType(int sortType){
        // 排序方式0表示正序，1表示倒序，2点赞最多
        if(sortType == 0){
            zheng.setTextColor(context.getResources().getColor(R.color.colorCommon,null));
            dao.setTextColor(context.getResources().getColor(R.color.grey,null));
            hot.setTextColor(context.getResources().getColor(R.color.grey,null));
        }else if(sortType == 1){
            zheng.setTextColor(context.getResources().getColor(R.color.grey,null));
            dao.setTextColor(context.getResources().getColor(R.color.colorCommon,null));
            hot.setTextColor(context.getResources().getColor(R.color.grey,null));
        }else if(sortType == 2){
            hot.setTextColor(context.getResources().getColor(R.color.colorCommon,null));
            zheng.setTextColor(context.getResources().getColor(R.color.grey,null));
            dao.setTextColor(context.getResources().getColor(R.color.grey,null));
        }
    }

    public void setZhengSelected(View.OnClickListener listener){
        zheng.setOnClickListener(listener);
    }

    public void setDaoSelected(View.OnClickListener listener){
        dao.setOnClickListener(listener);
    }

    public void setHotSelected(View.OnClickListener listener){
        hot.setOnClickListener(listener);
    }
}
