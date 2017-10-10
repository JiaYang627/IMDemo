package com.jiayang.imdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.util.DensityUtil;
import com.jiayang.imdemo.R;
import com.jiayang.imdemo.utils.StringUtils;
import com.jiayang.imdemo.v.adapter.IContactAdapter;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-06 17:01.
 */

public class SlideBar extends View {

    private static final String[] SECTIONS = {"搜","A","B","C","D","E","F","G","H","I","J"
            ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private Paint mPaint;
    private int mAvgWidth;
    private int mAvgHeight;
    private TextView mTvFloat;
    private RecyclerView mRecyclerView;


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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                setBackgroundResource(R.drawable.shape_sliderbar_background);
                showFloatAndScrollRecyclerView(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                if (mTvFloat!=null){
                    mTvFloat.setVisibility(GONE);
                }
                break;
        }

        return true;

    }

    private void showFloatAndScrollRecyclerView(float y) {
        /**
         * 根据y坐标计算点中的文本
         */
        int index = (int) (y/mAvgHeight);
        if (index<0){
            index = 0;
        }else if (index>SECTIONS.length-1){
            index = SECTIONS.length - 1;
        }
        String section = SECTIONS[index];
        /**
         * 获取FloatTitle(先让SlideBar找父控件，然后让父控件找FloatTitle)，然后设置section
         */
        if (mTvFloat ==null){
            ViewGroup parent = (ViewGroup) getParent();
            mTvFloat = (TextView) parent.findViewById(R.id.contact_textView);
            mRecyclerView = (RecyclerView) parent.findViewById(R.id.contact_recyclerView);
        }
        mTvFloat.setVisibility(VISIBLE);
        mTvFloat.setText(section);
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof IContactAdapter){
            IContactAdapter contactAdapter = (IContactAdapter) adapter;
            List<String> data = contactAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                String contact = data.get(i);
                if (section.equals(StringUtils.getInitial(contact))){
                    mRecyclerView.smoothScrollToPosition(i);
                    return;
                }
            }
        }else{
            throw  new RuntimeException("使用SlideBar时绑定的Adapter必须实现IContactAdapter接口");
        }
    }
}
