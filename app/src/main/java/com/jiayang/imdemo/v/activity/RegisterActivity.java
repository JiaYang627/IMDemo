package com.jiayang.imdemo.v.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.common.Constants;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.RegisterActivityPst;
import com.jiayang.imdemo.utils.CommonUtil;
import com.jiayang.imdemo.utils.PreferenceTool;
import com.jiayang.imdemo.utils.StringUtils;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.IregisterActivityView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册 Act
 */

public class RegisterActivity extends BaseActivity<RegisterActivityPst> implements IregisterActivityView {
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.til_username)
    TextInputLayout mTilUsername;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.til_pwd)
    TextInputLayout mTilPwd;
    @BindView(R.id.btn_regist)
    Button mBtnRegist;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();

    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {

        String userName = mEtUsername.getText().toString().trim();
        String userPwd = mEtPwd.getText().toString().trim();

        if (!StringUtils.checkUsername(userName)) {
            mTilUsername.setErrorEnabled(true);
            mTilUsername.setError("用户名不合法");

            mEtUsername.requestFocus(View.FOCUS_RIGHT);
            return;
        } else {
            mTilUsername.setErrorEnabled(false);
        }

        if (!StringUtils.checkPwd(userPwd)) {
            mTilPwd.setErrorEnabled(true);
            mTilPwd.setError("密码为3-20位的数字组合");
            mEtPwd.requestFocus(View.FOCUS_RIGHT);
            return;
        } else {
            mTilPwd.setErrorEnabled(false);
        }
        showDialog("正在注册......");
        mPresenter.goToRegist(userName, userPwd);

    }

    @Override
    public void fillData(String userName, String userPwd, boolean isSuccess, String errorMsg) {
        dismissDialog();
        if (isSuccess) {

            PreferenceTool.putString(Constants.SP_Info.SP_USER_NAME, userName);
            PreferenceTool.commit();
            PreferenceTool.putString(Constants.SP_Info.SP_USER_PWD, userPwd);
            PreferenceTool.commit();
            ToastUtils.initToast("注册成功");

            CommonUtil.hideSoftKeyboard((Activity)context);
            this.finish();

        } else {
            ToastUtils.initToast("注册失败" + errorMsg);
        }
    }
}
