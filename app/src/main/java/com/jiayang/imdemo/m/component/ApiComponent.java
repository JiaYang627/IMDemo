package com.jiayang.imdemo.m.component;



import com.jiayang.imdemo.m.model.ApiModule;
import com.jiayang.imdemo.v.activity.LoginActivity;
import com.jiayang.imdemo.v.activity.MainActivity;
import com.jiayang.imdemo.v.activity.RegisterActivity;
import com.jiayang.imdemo.v.activity.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/8/31 0031.
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);
}
