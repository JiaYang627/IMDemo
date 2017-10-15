package com.jiayang.imdemo.v.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.ChatActivityPst;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.IchatActivityView;

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
        Intent intent = getIntent();
        //聊天对象
        mUsername = intent.getStringExtra("username");
        mTvTitle.setText("与"+ mUsername +"聊天中");


        String msg = mEditText.getText().toString();
        if (TextUtils.isEmpty(msg)){
            mSendButton.setEnabled(false);
        }else {
            mSendButton.setEnabled(true);
        }

        mEditText.addTextChangedListener(this);
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
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length()==0){
            mSendButton.setEnabled(false);
        }else{
            mSendButton.setEnabled(true);
        }

    }
}
