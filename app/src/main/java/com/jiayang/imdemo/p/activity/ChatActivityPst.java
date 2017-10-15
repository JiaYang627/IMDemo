package com.jiayang.imdemo.p.activity;

import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.activity.ChatActivity;
import com.jiayang.imdemo.v.iview.IchatActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-15 10:37.
 */

public class ChatActivityPst extends BasePresenter<IchatActivityView> {

    private IMService mIMService;

    @Inject
    public ChatActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }


}
