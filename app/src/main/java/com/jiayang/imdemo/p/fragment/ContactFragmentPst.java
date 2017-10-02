package com.jiayang.imdemo.p.fragment;

import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.IcontactFragmentView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-02 14:50.
 */

public class ContactFragmentPst extends BasePresenter<IcontactFragmentView> {

    private IMService mIMService;

    @Inject
    public ContactFragmentPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }
}
