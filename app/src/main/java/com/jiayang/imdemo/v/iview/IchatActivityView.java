package com.jiayang.imdemo.v.iview;

import com.hyphenate.chat.EMMessage;
import com.jiayang.imdemo.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-15 10:37.
 */

public interface IchatActivityView extends IBaseView {
    void fillData(String username);

    void fillChatMessage(List<EMMessage> emMessageList);

    void onUpdate(int size);
}
