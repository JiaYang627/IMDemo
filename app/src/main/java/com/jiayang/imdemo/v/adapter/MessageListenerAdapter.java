package com.jiayang.imdemo.v.adapter;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by 张 奎 on 2017-10-16 14:55.
 */

public class MessageListenerAdapter implements EMMessageListener {
    @Override
    public void onMessageReceived(List<EMMessage> list) {
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
    }
}