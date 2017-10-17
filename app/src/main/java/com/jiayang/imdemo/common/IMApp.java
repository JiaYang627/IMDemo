package com.jiayang.imdemo.common;

import android.app.Application;
import android.content.Context;

import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.v.base.BaseActivity;

/**
 * Created by 张 奎 on 2017-09-30 18:04.
 */

public class IMApp extends Application {

    private IMAppDeletage mImAppDeletage;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mImAppDeletage = new IMAppDeletage(this);
        mImAppDeletage.onCreat();
        sContext = this;

    }
    public static Context getContext() {
        return sContext;
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        mImAppDeletage.onTerminate();
    }

    public ApiComponent getApiComponent(){
        return mImAppDeletage.getApiComponent();
    }

    public void addActiviy(BaseActivity activity) {
        mImAppDeletage.addActivity(activity);

    }

    public void removeActivity(BaseActivity activity) {
        mImAppDeletage.removeActivity(activity);
    }
}
