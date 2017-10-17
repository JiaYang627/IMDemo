package com.jiayang.imdemo.v.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.fragment.ConversationFragmentPst;
import com.jiayang.imdemo.v.activity.MainActivity;
import com.jiayang.imdemo.v.adapter.ConversationAdapter;
import com.jiayang.imdemo.v.base.BaseFragment;
import com.jiayang.imdemo.v.iview.IconversationFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-10-02 14:46.
 */

public class ConversationFragment extends BaseFragment<ConversationFragmentPst> implements IconversationFragmentView, ConversationAdapter.onItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private Unbinder mUnbinder;
    private ConversationAdapter mConversationAdapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_conversation, null);
        mUnbinder = ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventBus.getDefault().register(this);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mConversationAdapter != null) {
            mConversationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        mConversationAdapter = null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage message) {
        mPresenter.getConversation();

    }

    @Override
    public void onInitConversation(List<EMConversation> emConversationList) {

        if (mConversationAdapter == null) {
            mConversationAdapter = new ConversationAdapter(emConversationList);
            mRecyclerView.setAdapter(mConversationAdapter);
            mConversationAdapter.setOnItemClickListener(this);
        } else {
            mConversationAdapter.notifyDataSetChanged();
        }

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        // 按钮作一个选择动画
        ObjectAnimator.ofFloat(mFab, "rotation", 0, 360).setDuration(1000).start();
        // 将消息全部致为已读
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
        // 首页 “消息”角标更新
        MainActivity activity = (MainActivity) getActivity();
        activity.updateUnreadCount();
        if (mConversationAdapter != null) {
            mConversationAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemClick(EMConversation emConversation) {
        mPresenter.goToChat(emConversation.getUserName());
    }
}
