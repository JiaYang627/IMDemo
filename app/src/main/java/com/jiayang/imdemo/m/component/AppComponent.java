package com.jiayang.imdemo.m.component;



import com.jiayang.imdemo.common.IMApp;
import com.jiayang.imdemo.m.model.AppModule;

import dagger.Component;

/**
 * Created by 张 奎 on 2017-09-01 09:02.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(IMApp mvpAppDeletage);
}
