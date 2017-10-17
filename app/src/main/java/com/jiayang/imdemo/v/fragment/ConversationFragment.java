package com.jiayang.imdemo.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.fragment.ConversationFragmentPst;
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

public class ConversationFragment extends BaseFragment<ConversationFragmentPst> implements IconversationFragmentView {

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
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mConversationAdapter != null) {
//            mConversationAdapter.notifyDataSetChanged();
//        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EMMessage message) {
        mPresenter.getConversation();

    }

    @Override
    public void onInitConversation(List<EMConversation> emConversationList) {

//        if (mConversationAdapter == null) {
//            mConversationAdapter = new ConversationAdapter(emConversationList);
//            mRecyclerView.setAdapter(mConversationAdapter);
//        } else {
//            mConversationAdapter.notifyDataSetChanged();
//        }

        // 不知道是否是环信后台的问题 正常逻辑 上面的写法 后来运行的时候发现列表显示不出来。
        // 无奈 只有这样写了
        mConversationAdapter = new ConversationAdapter(emConversationList);
        mRecyclerView.setAdapter(mConversationAdapter);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
    }
}
