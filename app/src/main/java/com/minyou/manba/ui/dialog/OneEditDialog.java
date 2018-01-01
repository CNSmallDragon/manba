package com.minyou.manba.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.minyou.manba.R;

/**
 * Created by luchunhao on 2017/12/28.
 */
public class OneEditDialog extends Dialog {

    private TextView ftv_msg_dialog;   // msg
    private TextView ftv_cancel_dialog;// 取消
    private TextView ftv_sure_dialog;  // 确定
    private EditText et_input_text;

    public OneEditDialog(Context context) {
        super(context);
    }

    public OneEditDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OneEditDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input_single);

        ftv_msg_dialog = (TextView) findViewById(R.id.tv_msg_dialog);
        ftv_cancel_dialog = (TextView) findViewById(R.id.tv_cancel_dialog);
        ftv_sure_dialog = (TextView) findViewById(R.id.tv_sure_dialog);
        et_input_text = (EditText) findViewById(R.id.et_input_text);
    }

    /**
     * 设置右键文字和点击事件
     *
     * @param rightStr 文字
     * @param clickListener 点击事件
     */
    public void setRightButton(String rightStr, View.OnClickListener clickListener) {
        ftv_sure_dialog.setOnClickListener(clickListener);
        ftv_sure_dialog.setText(rightStr);
    }

    /**
     * 设置左键文字和点击事件
     *
     * @param leftStr 文字
     * @param clickListener 点击事件
     */
    public void setLeftButton(String leftStr, View.OnClickListener clickListener) {
        ftv_cancel_dialog.setOnClickListener(clickListener);
        ftv_cancel_dialog.setText(leftStr);
    }

    /**
     * 设置提示内容
     *
     * @param str 内容
     */
    public void setHintText(String str) {
        ftv_msg_dialog.setText(str);
        ftv_msg_dialog.setVisibility(View.VISIBLE);
    }

    public String getInputString(){
        return et_input_text.getText().toString().trim();
    }
}
