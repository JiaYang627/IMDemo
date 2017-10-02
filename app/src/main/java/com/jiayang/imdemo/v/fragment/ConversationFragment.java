package com.jiayang.imdemo.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.fragment.ConversationFragmentPst;
import com.jiayang.imdemo.v.base.BaseFragment;
import com.jiayang.imdemo.v.iview.IconversationFragmentView;

/**
 * Created by 张 奎 on 2017-10-02 14:46.
 */

public class ConversationFragment extends BaseFragment<ConversationFragmentPst> implements IconversationFragmentView{
    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_conversation, null);
        return view;

    }
}
