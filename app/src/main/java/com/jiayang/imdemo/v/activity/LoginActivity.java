package com.jiayang.imdemo.v.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.LoginActivityPst;
import com.jiayang.imdemo.utils.StringUtils;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.IloginActivityView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录 Act
 */

public class LoginActivity extends BaseActivity<LoginActivityPst> implements IloginActivityView {
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.til_username)
    TextInputLayout mTilUsername;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.til_pwd)
    TextInputLayout mTilPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_newuser)
    TextView mTvNewuser;
    private int REQUEST_SDCARD = 1;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
    }

    private void checkInfo() {
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


        /**
         *  6.0 动态申请权限。环信在登录的时候 会对SD进行读写操作。
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_SDCARD);
            return;
        }

        showDialog("正在玩命登录中......");
        mPresenter.goToLogin(userName, userPwd);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SDCARD) {
            // 被授权
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                checkInfo();
            } else {
                ToastUtils.initToast("您未授权相应权限，无法登录。");
            }
        }
    }

    @Override
    public void fillData(String userName, String userPwd, boolean isSuccess, String msg) {
        if (isSuccess) {
            // 成功了
            saveUserInfo(userName , userPwd);
            mPresenter.goToMain();

        } else {
            // 失败了
            ToastUtils.initToast("登录失败："+ msg);
        }
    }

    @OnClick({R.id.btn_login, R.id.tv_newuser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:    // 登录
                checkInfo();
                break;
            case R.id.tv_newuser:   // 注册
                mPresenter.goToRegister();
                break;
        }
    }
}
