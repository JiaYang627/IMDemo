package com.jiayang.imdemo.p.activity;

import com.hyphenate.chat.EMClient;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.IsplashActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-01 10:14.
 */

public class SplashActivityPst extends BasePresenter<IsplashActivityView> {


    private IMService mIMService;

    @Inject
    public SplashActivityPst(ErrorListener errorListener, IMService imService) {
        super(errorListener);
        mIMService = imService;
    }

    public void checkedLogin() {

        // isLoggedInBefore 之前是否登录  isLoggedInBefore 是否已经连接上
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isLoggedInBefore()) {
            // 已经登录了
            mView.fillData(true);
        } else {
            // 还没有登录
            mView.fillData(false);

        }

    }

    public void goToMain() {
        mIMNavigate.goToMain(context);
    }

    public void goToLogin() {
        mIMNavigate.goToLogin(context);
    }
}
