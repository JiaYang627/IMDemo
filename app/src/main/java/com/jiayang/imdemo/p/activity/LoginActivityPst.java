package com.jiayang.imdemo.p.activity;

import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.IloginActivityView;

import javax.inject.Inject;

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

    public void goToRegister() {
        mIMNavigate.goToRegister(context);
    }
}
