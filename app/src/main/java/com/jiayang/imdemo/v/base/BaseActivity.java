package com.jiayang.imdemo.v.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;


import com.jiayang.imdemo.common.IMApp;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.CommonUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected Context context;
    private Unbinder unbinder;


    @Inject
    protected T mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        inject(((IMApp)getApplication()).getApiComponent());
        mPresenter.attachView(this);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mPresenter.getContext(context);             //此方法是给P 传递 当前Act的上下文 必须写在getData前面
        mPresenter.getData(getIntent());

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onTakeView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void inject(ApiComponent apiComponent);
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unbinder.unbind();
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CommonUtil.hideSoftKeyboard(this);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
