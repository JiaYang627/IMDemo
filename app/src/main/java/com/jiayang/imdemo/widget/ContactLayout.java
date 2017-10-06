package com.jiayang.imdemo.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiayang.imdemo.R;

/**
 * Created by 张 奎 on 2017-10-06 16:52.
 */

public class ContactLayout extends RelativeLayout {
    public ContactLayout(Context context) {
        this(context,null);
    }

    public ContactLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_contact, this, true);
        RecyclerView contactRecyclerView = (RecyclerView) findViewById(R.id.contact_recyclerView);
        TextView contactTextView = (TextView) findViewById(R.id.contact_textView);
        SlideBar contactSlideBar = (SlideBar) findViewById(R.id.contact_slideBar);

    }

    public ContactLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ContactLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
