package com.jiayang.imdemo.v.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiayang.imdemo.R;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.p.activity.MainActivityPst;
import com.jiayang.imdemo.v.base.BaseActivity;
import com.jiayang.imdemo.v.iview.ImainActivityView;

public class MainActivity extends BaseActivity<MainActivityPst> implements ImainActivityView{

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
