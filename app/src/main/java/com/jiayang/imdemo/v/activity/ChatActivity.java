package com.jiayang.imdemo.v.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.ChatActivityPst;
import com.jiayang.imdemo.v.adapter.ChatAdapter;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.IchatActivityView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 张 奎 on 2017-10-15 10:36.
 */

public class ChatActivity extends BaseActivity<ChatActivityPst> implements IchatActivityView, TextWatcher {
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.sendButton)
    Button mSendButton;
    private String mUsername;
    private ChatAdapter mChatAdapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initShow();
    }

    private void initShow() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String msg = mEditText.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            mSendButton.setEnabled(false);
        } else {
            mSendButton.setEnabled(true);
        }

        mEditText.addTextChangedListener(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage message) {
        //当收到信消息的时候
        /*
         *  判断当前这个消息是不是正在聊天的用户给我发的
         *  如果是，让ChatPresenter 更新数据
         *
         */
        String from = message.getFrom();
        if (from.equals(mUsername)) {
            mPresenter.updateData(mUsername);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;

    }

    @OnClick(R.id.sendButton)
    public void onViewClicked() {
        String sendMessage = mEditText.getText().toString().trim();
        mPresenter.sendMessage(mUsername, sendMessage);
        mEditText.getText().clear();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 0) {
            mSendButton.setEnabled(false);
        } else {
            mSendButton.setEnabled(true);
        }

    }

    @Override
    public void fillData(String username) {
        this.mUsername = username;
        mTvTitle.setText("与" + username + "聊天中");
    }

    @Override
    public void fillChatMessage(List<EMMessage> emMessageList) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(emMessageList);
        mRecyclerView.setAdapter(mChatAdapter);
        if (emMessageList.size() != 0) {
            mRecyclerView.scrollToPosition(emMessageList.size() - 1);
        }
    }

    @Override
    public void onUpdate(int size) {
        mChatAdapter.notifyDataSetChanged();
        if (size != 0) {
            mRecyclerView.smoothScrollToPosition(size - 1);
        }
    }
}
