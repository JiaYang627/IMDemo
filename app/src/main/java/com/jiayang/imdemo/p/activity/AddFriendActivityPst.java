package com.jiayang.imdemo.p.activity;

import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.IaddFriendActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-13 08:15.
 */

public class AddFriendActivityPst extends BasePresenter<IaddFriendActivityView> {


    private IMService mIMService;

    @Inject
    public AddFriendActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);

        mIMService = imService;
    }
}
