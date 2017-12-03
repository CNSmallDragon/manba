package com.minyou.manba.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.minyou.manba.R;

/**
 * Created by Administrator on 2017/12/2.
 */
public class BorderTextView extends TextView {

    private boolean borders = false;
    private boolean bordersLeft = false;
    private boolean bordersTop = false;
    private boolean bordersRight = false;
    private boolean bordersBottom = false;
    private int borderHeight;

    private int lineColor;
    private final Paint paint = new Paint();

    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取属性集
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView);
        borders = typedArray.getBoolean(R.styleable.BorderTextView_layout_borders, false);
        bordersLeft = typedArray.getBoolean(R.styleable.BorderTextView_layout_borderLeft, false);
        bordersTop = typedArray.getBoolean(R.styleable.BorderTextView_layout_borderTop, false);
        bordersRight = typedArray.getBoolean(R.styleable.BorderTextView_layout_borderRight, false);
        bordersBottom = typedArray.getBoolean(R.styleable.BorderTextView_layout_borderBottom, false);
        borderHeight = (int) typedArray.getDimension(R.styleable.BorderTextView_layout_borderHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        // 获取文本颜色，保持一致
        lineColor = this.getCurrentTextColor();
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(lineColor);
        if (borders) {
            canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
            canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
            canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
                    this.getHeight() - 1, paint);
            canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
                    this.getHeight() - 1, paint);

        }

        if (bordersBottom) {
            canvas.drawRect(0, getHeight() - borderHeight, getWidth(), getHeight(), paint);
//            canvas.drawLine(0, this.getHeight() - borderHeight, this.getWidth(), this.getHeight()- borderHeight, paint);
        }
    }

    public void isChecked(boolean checked) {
        bordersBottom = checked;
        if (checked) {
            // 设置字体颜色
            this.setTextColor(Color.BLACK);
            lineColor = Color.BLACK;
        } else {
            this.setTextColor(Color.GRAY);
            lineColor = Color.GRAY;
        }
        invalidate();
    }

}
