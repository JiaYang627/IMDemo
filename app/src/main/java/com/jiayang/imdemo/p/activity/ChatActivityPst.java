package com.jiayang.imdemo.p.activity;

import android.content.Intent;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jiayang.imdemo.m.listener.CallBackListener;
import com.jiayang.imdemo.m.rxhelper.ErrorListener;
import com.jiayang.imdemo.m.service.IMService;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.v.activity.ChatActivity;
import com.jiayang.imdemo.v.iview.IchatActivityView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-10-15 10:37.
 */

public class ChatActivityPst extends BasePresenter<IchatActivityView> {

    private IMService mIMService;
    private String mUsername;
    private List<EMMessage> mEMMessageList = new ArrayList<>();

    @Inject
    public ChatActivityPst(ErrorListener errorListener , IMService imService) {
        super(errorListener);
        mIMService = imService;
    }

    @Override
    public void getData(Intent intent) {
        super.getData(intent);
        mUsername = intent.getStringExtra("username");
        mView.fillData(mUsername);
    }

    @Override
    public void onOnceTakeView() {
        super.onOnceTakeView();

        initChatMessage(mUsername);
        mView.fillChatMessage(mEMMessageList);

    }

    private void initChatMessage(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if (conversation != null) {
            //需要将所有的未读消息标记为已读
            conversation.markAllMessagesAsRead();

            //曾经有聊天过
            //那么获取最多最近的20条聊天记录，然后展示到View层
            //获取最后一条消息
            EMMessage lastMessage = conversation.getLastMessage();
            //获取最后一条消息之前的19条（最多）
            int count = 19;
            if (mEMMessageList.size()>=19){
                count = mEMMessageList.size();
            }
            List<EMMessage> messageList = conversation.loadMoreMsgFromDB(lastMessage.getMsgId(), count);
            Collections.reverse(messageList);
            mEMMessageList.clear();
            mEMMessageList.add(lastMessage);
            mEMMessageList.addAll(messageList);
            Collections.reverse(mEMMessageList);
        } else {
            mEMMessageList.clear();
        }


    }

    public void updateData(String currentUser) {
        initChatMessage(currentUser);
        mView.onUpdate(mEMMessageList.size());
    }

    public void sendMessage(String username, String msg) {
        EMMessage emMessage = EMMessage.createTxtSendMessage(msg,username);
        emMessage.setStatus(EMMessage.Status.INPROGRESS);
        mEMMessageList.add(emMessage);
        mView.onUpdate(mEMMessageList.size());
        emMessage.setMessageStatusCallback(new CallBackListener() {
            @Override
            public void onMainSuccess() {
                mView.onUpdate(mEMMessageList.size());
            }
            @Override
            public void onMainError(int i, String s) {
                mView.onUpdate(mEMMessageList.size());
            }
        });

        EMClient.getInstance().chatManager().sendMessage(emMessage);


    }
}
