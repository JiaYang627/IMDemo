package com.jiayang.imdemo.p.fragment;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jiayang.imdemo.m.listener.CallBackListener;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.ThreadUtils;
import com.jiayang.imdemo.v.iview.IpluginFragmentView;

import javax.inject.Inject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by 张 奎 on 2017-10-02 14:53.
 */

public class PluginFragmentPst extends BasePresenter<IpluginFragmentView> {


    private IMService mIMService;

    @Inject
    public PluginFragmentPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }

    /**
     *  退出
     */
    public void goLogout() {

        /**
         *  true 退出后不在接收官方推送的消息。
         */
        EMClient.getInstance().logout(true, new CallBackListener() {
            @Override
            public void onMainSuccess() {
                mView.fillData(EMClient.getInstance().getCurrentUser(),true ,null);
            }

            @Override
            public void onMainError(int i, String s) {
                mView.fillData(EMClient.getInstance().getCurrentUser(),false ,s);
            }
        });
    }
}
