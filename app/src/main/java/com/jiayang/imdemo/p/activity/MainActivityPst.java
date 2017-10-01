package com.jiayang.imdemo.p.activity;

import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.ImainActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-01 10:20.
 */

public class MainActivityPst extends BasePresenter<ImainActivityView> {

    private IMService mIMService;

    @Inject
    public MainActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }
}
