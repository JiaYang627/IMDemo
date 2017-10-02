package com.jiayang.imdemo.p.activity;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.ThreadUtils;
import com.jiayang.imdemo.v.iview.IloginActivityView;

import javax.inject.Inject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by 张 奎 on 2017-10-01 10:40.
 */

public class LoginActivityPst extends BasePresenter<IloginActivityView> {

    private IMService mIMService;

    @Inject
    public LoginActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }

    /**
     *  注册
     */
    public void goToRegister() {
        mIMNavigate.goToRegister(context);
    }


    /**
     *  登录
     * @param userName
     * @param userPwd
     */
    public void goToLogin(final String userName, final String userPwd) {
        // 环信目前(3.5.X)的所有回调都是在子线程中。
        EMClient.getInstance().login(userName, userPwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.fillData(userName, userPwd, true, null);
                    }
                });
            }

            @Override
            public void onError(int i, final String s) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.fillData(userName, userPwd, false, s);
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void goToMain() {
        mIMNavigate.goToMain(context);
    }
}
