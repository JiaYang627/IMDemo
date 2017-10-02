package com.jiayang.imdemo.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.fragment.PluginFragmentPst;
import com.jiayang.imdemo.v.base.BaseFragment;
import com.jiayang.imdemo.v.iview.IpluginFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-10-02 14:52.
 */

public class PluginFragment extends BaseFragment<PluginFragmentPst> implements IpluginFragmentView {

    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    private Unbinder mUnbinder;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_plugin, null);
        mUnbinder = ButterKnife.bind(this, view);

        mBtnLogout.setText("退(" + getUserName() + ")出");
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        showDialog("正在退出登录");
        mPresenter.goLogout();
    }

    @Override
    public void fillData(String currentUser, boolean isSuccess, String msg) {
        dismissDialog();
        if (isSuccess) {

        } else {

        }
    }
}
