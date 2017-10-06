package com.jiayang.imdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.hyphenate.util.DensityUtil;

/**
 * Created by 张 奎 on 2017-10-06 17:01.
 */

public class SlideBar extends View {

    private static final String[] SECTIONS = {"搜","A","B","C","D","E","F","G","H","I","J"
            ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private Paint mPaint;
    private int mAvgWidth;
    private int mAvgHeight;


    public SlideBar(Context context) {
        this(context, null);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        // 边角齿化，最大限度防止边角锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setTextSize(DensityUtil.sp2px(getContext(),10));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor("#9c9c9c"));
    }

    public SlideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    /**
     *  5.0以后谷歌推出此构造，运行低版本的时候不会走此构造。
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlideBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mAvgWidth = getMeasuredWidth() / 2;
        mAvgHeight = getMeasuredHeight() / SECTIONS.length;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            canvas.drawText(SECTIONS[i], mAvgWidth, mAvgHeight * (i + 1), mPaint);
        }
    }
}
