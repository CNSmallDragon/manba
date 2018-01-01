package com.minyou.manba.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.minyou.manba.R;

/**
 * Created by luchunhao on 2017/12/28.
 */
public class NoteDoubleBtnDialogFragment extends BaseDialogFragment {

    private static OnDialogRightBtnClickedListener mRightBtnListener;
    private static OnDialogLeftBtnClickedListener mLeftBtnListener;

    private static String msg;// 标题
    private static String leftBtnText; // 左边按钮的文字
    private static String rightBtnText;// 右边按钮的文字

    private TextView ftv_msg_dialog;   // msg
    private TextView ftv_cancel_dialog;// 取消
    private TextView ftv_sure_dialog;  // 确定

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light_NoTitleBar);// 去掉标题栏
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_input_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ftv_msg_dialog = (TextView) view.findViewById(R.id.tv_msg_dialog);
        ftv_cancel_dialog = (TextView) view.findViewById(R.id.tv_cancel_dialog);
        ftv_sure_dialog = (TextView) view.findViewById(R.id.tv_sure_dialog);

        if (!TextUtils.isEmpty(msg)) {
            ftv_msg_dialog.setText(msg);
        }
        if (!TextUtils.isEmpty(leftBtnText)) {
            ftv_cancel_dialog.setText(leftBtnText);
        }
        if (!TextUtils.isEmpty(rightBtnText)) {
            ftv_sure_dialog.setText(rightBtnText);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ftv_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftBtnListener != null) {
                    mLeftBtnListener.onDialogLeftBtnClick();
                }
                dismissAllowingStateLoss();
            }
        });
        ftv_sure_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightBtnListener != null) {
                    mRightBtnListener.onDialogRightBtnClick();
                }
                dismissAllowingStateLoss();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRightBtnListener = null;
        mLeftBtnListener = null;
        System.gc();
    }


    // 回调接口 确定按钮的回调
    public interface OnDialogRightBtnClickedListener {
        void onDialogRightBtnClick();
    }

    // 取消按钮的回调
    public interface OnDialogLeftBtnClickedListener {
        void onDialogLeftBtnClick();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        mRightBtnListener = null;
        mLeftBtnListener = null;
        System.gc();
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        mRightBtnListener = null;
        mLeftBtnListener = null;
        System.gc();
    }

    // 建造者类
    public static class Builder {

        public Builder() {

        }

        // 设置标题
        public Builder setTitle(String title) {
            msg = title;
            return this;
        }

        // 设置确定按钮的点击事件
        public Builder setOnDialogRightBtnClickedListener(OnDialogRightBtnClickedListener mListener) {
            mRightBtnListener = mListener;
            return this;
        }

        // 设置取消按钮的点击事件
        public Builder setOnDialogLeftBtnClickedListener(OnDialogLeftBtnClickedListener mListener) {
            mLeftBtnListener = mListener;
            return this;
        }

        // 设置左边按钮的文字
        public Builder setLeftBtnText(String text) {
            leftBtnText = text;
            return this;
        }

        // 设置右边按钮的文字
        public Builder setRightBtnText(String text) {
            rightBtnText = text;
            return this;
        }

        // 显示弹窗
        public NoteDoubleBtnDialogFragment show(FragmentManager manager, String tag) {
            NoteDoubleBtnDialogFragment fragment = new NoteDoubleBtnDialogFragment();
            fragment.show(manager, tag);
            return fragment;
        }

        public NoteDoubleBtnDialogFragment show(FragmentTransaction transaction, String tag) {
            NoteDoubleBtnDialogFragment fragment = new NoteDoubleBtnDialogFragment();
            fragment.show(transaction, tag);
            return fragment;
        }
    }
}
