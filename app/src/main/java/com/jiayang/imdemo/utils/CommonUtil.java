package com.jiayang.imdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by 张 奎 on 2017-10-01 17:05.
 */

public class CommonUtil {


    /**
     * 强制隐藏键盘
     *
     * @param context
     */
    public static void hideKeyBoard(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 强制隐藏键盘,应用于activity
     *
     * @param context
     */
    public static void hideSoftKeyboard(Activity context) {
        View focus_view = context.getCurrentFocus();
        if (focus_view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(focus_view.getWindowToken(), 0);
        }
    }
}
