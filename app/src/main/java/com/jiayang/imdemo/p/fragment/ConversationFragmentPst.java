package com.jiayang.imdemo.p.fragment;

import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.IconversationFragmentView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-02 14:46.
 */

public class ConversationFragmentPst extends BasePresenter<IconversationFragmentView> {


    private IMService mIMService;

    @Inject
    public ConversationFragmentPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService =imService;
    }
}
