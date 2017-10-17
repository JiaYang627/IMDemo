package com.jiayang.imdemo.v.iview;

import com.hyphenate.chat.EMConversation;
import com.jiayang.imdemo.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-02 14:47.
 */

public interface IconversationFragmentView extends IBaseView {
    void onInitConversation(List<EMConversation> emConversationList);
}
