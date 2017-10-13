package com.jiayang.imdemo.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jiayang.imdemo.v.activity.AddFriendActivity;
import com.jiayang.imdemo.v.activity.LoginActivity;
import com.jiayang.imdemo.v.activity.MainActivity;
import com.jiayang.imdemo.v.activity.RegisterActivity;

/**
 * Created by 张 奎 on 2017-10-01 09:56.
 */

public class IMNavigate {
    public void goToMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public void goToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public void goToRegister(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);

    }

    public void goToAddFriend(Context context) {
        Intent intent = new Intent(context, AddFriendActivity.class);
        context.startActivity(intent);
    }
}
