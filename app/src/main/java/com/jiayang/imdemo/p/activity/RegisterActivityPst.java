package com.jiayang.imdemo.p.activity;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jiayang.imdemo.m.bean.User;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.ThreadUtils;
import com.jiayang.imdemo.v.iview.IregisterActivityView;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 张 奎 on 2017-10-01 11:43.
 */

public class RegisterActivityPst extends BasePresenter<IregisterActivityView> {


    private IMService mIMService;

    @Inject
    public RegisterActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }

    /**
     * 注册
     *
     * 1. 先注册Bmob云数据库
     * 2. 如果Bmob成功了再去注册环信平台
     * 3. 如果Bmob成功了，环信失败了，则再去把Bmob上的数据给删除掉
     *
     * @param userName
     * @param userPwd
     */
    public void goToRegist(final String userName, final String userPwd) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(userPwd);

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if (e == null) {
                    // 成功了
                    ThreadUtils.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(userName ,userPwd);
                                // 成功
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mView.fillData(userName ,userPwd , true ,null);
                                    }
                                });


                            } catch (final HyphenateException e1) {
                                e1.printStackTrace();
                                // 将注册的 user 删除掉
                                user.delete();
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mView.fillData(userName , userPwd ,false ,e1.toString());
                                    }
                                });
                            }
                        }
                    });

                } else {
                    // 失败
                    mView.fillData(userName, userPwd, false, e.getMessage());


                }
            }
        });

    }
}
