package com.jiayang.imdemo.m.model;

import android.app.Application;

import com.jiayang.imdemo.common.IMNavigate;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 张 奎 on 2017-09-01 08:58.
 */
@Module
public class AppModule {


    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    IMNavigate provideWyNavigate() {
        return new IMNavigate();
    }
}
