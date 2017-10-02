package com.jiayang.imdemo.m.listener;

import com.hyphenate.EMCallBack;
import com.jiayang.imdemo.utils.ThreadUtils;

/**
 * Created by 张 奎 on 2017-10-02 15:53.
 */

public abstract class CallBackListener implements EMCallBack {

    public  abstract void onMainSuccess();

    public abstract void onMainError(int i, String s);

    @Override
    public void onSuccess() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainSuccess();
            }
        });
    }

    @Override
    public void onError(final int i, final String s) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onMainError(i,s);
            }
        });
    }

    @Override
    public void onProgress(int i, String s) {

    }
}
