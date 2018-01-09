package com.minyou.manba.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.minyou.manba.R;

/**
 * Created by luchunhao on 2018/1/9.
 */

public class PointInfoDialog extends Dialog {

    private Context context;

    private TextView tv_dialog_title;
    private TextView ftv_cancel_dialog;
    private TextView ftv_sure_dialog;

    public PointInfoDialog(@NonNull Context context) {
        this(context,0);
    }

    public PointInfoDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_point_info);

        tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        ftv_cancel_dialog = (TextView) findViewById(R.id.ftv_cancel_dialog);
        ftv_sure_dialog = (TextView) findViewById(R.id.ftv_sure_dialog);

    }

    public void setDialogTitle(String title){
        tv_dialog_title.setText(title);
    }

    public void setCancelButtonClickListener(View.OnClickListener listener){
        ftv_cancel_dialog.setOnClickListener(listener);
    }

    public void setOKButtonClickListener(View.OnClickListener listener){
        ftv_sure_dialog.setOnClickListener(listener);
    }
}
