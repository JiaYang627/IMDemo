package com.jiayang.imdemo.v.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.SplashActivityPst;
import com.jiayang.imdemo.v.adapter.AnimatorListenerAdapter;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.IsplashActivityView;

import butterknife.BindView;

/**
 * Created by 张 奎 on 2017-10-01 10:13.
 */

public class SplashActivity extends BaseActivity<SplashActivityPst> implements IsplashActivityView {
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    private long SplashDuration = 2000;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        // 判断是否已经登录
        mPresenter.checkedLogin();
    }

    @Override
    public void fillData(boolean isLogin) {
        if (isLogin) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mIvSplash, "alpha", 1, 0)
                    .setDuration(SplashDuration);
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mPresenter.goToMain();
                }
            });
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mIvSplash, "alpha", 1, 0)
                    .setDuration(SplashDuration);
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mPresenter.goToLogin();
                }
            });
        }
    }
}
