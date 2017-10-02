package com.jiayang.imdemo.v.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jiayang.imdemo.common.Constants;
import com.jiayang.imdemo.common.IMApp;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.PreferenceTool;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-08-31 16:56.
 */

public abstract class BaseFragment <T extends BasePresenter> extends AppCompatDialogFragment implements IBaseView{

    @Inject
    protected T mPresenter;
    protected boolean presenterFactoryPrepared;
    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;
    private ProgressDialog mProgressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((IMApp)getActivity().getApplication()).getApiComponent());
        mPresenter.attachView(this);
        mPresenter.getContext(getActivity());
        mPresenter.getData(getActivity().getIntent());
        mPresenter.getArguments(getArguments());

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);

    }
    protected abstract void inject(ApiComponent apiComponent);

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onTakeView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mPresenter.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode ,resultCode ,data);
    }

    public void saveUserInfo(String userName ,String userPwd) {
        PreferenceTool.putString(Constants.SP_Info.SP_USER_NAME, userName);
        PreferenceTool.commit();
        PreferenceTool.putString(Constants.SP_Info.SP_USER_PWD, userPwd);
        PreferenceTool.commit();
    }

    public String getUserName() {
        String userName = PreferenceTool.getString(Constants.SP_Info.SP_USER_NAME, "");
        return userName;
    }
    public String getUserPwd() {
        String userPwd = PreferenceTool.getString(Constants.SP_Info.SP_USER_PWD, "");
        return userPwd;
    }

    public void showDialog(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
