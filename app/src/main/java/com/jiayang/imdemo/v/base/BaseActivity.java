package com.jiayang.imdemo.v.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.jiayang.imdemo.common.Constants;
import com.jiayang.imdemo.common.IMApp;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.base.BasePresenter;
import com.jiayang.imdemo.utils.CommonUtil;
import com.jiayang.imdemo.utils.PreferenceTool;

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

    public void setStatusBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
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

}
