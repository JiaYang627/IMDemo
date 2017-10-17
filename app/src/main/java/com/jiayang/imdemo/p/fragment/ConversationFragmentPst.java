package com.jiayang.imdemo.p.fragment;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.iview.IconversationFragmentView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-02 14:46.
 */

public class ConversationFragmentPst extends BasePresenter<IconversationFragmentView> {


    private IMService mIMService;
    private List<EMConversation> mEMConversationList = new ArrayList<>();

    @Inject
    public ConversationFragmentPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService =imService;
    }

    @Override
    public void onOnceTakeView() {
        super.onOnceTakeView();

        getConversation();
    }

    public void getConversation() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        mEMConversationList.clear();
        mEMConversationList.addAll(allConversations.values());
        /**
         * 排序，最近的时间在最上面(时间的倒序)
         * 回传到View层
         */
        Collections.sort(mEMConversationList, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {

                return (int) (o2.getLastMessage().getMsgTime()-o1.getLastMessage().getMsgTime());
            }
        });
        mView.onInitConversation(mEMConversationList);
    }
}
