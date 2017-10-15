package com.jiayang.imdemo.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;
import com.jiayang.imdemo.BuildConfig;
import com.jiayang.imdemo.db.DBUtils;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.m.component.AppComponent;
import com.jiayang.imdemo.m.component.DaggerApiComponent;
import com.jiayang.imdemo.m.component.DaggerAppComponent;
import com.jiayang.imdemo.m.model.ApiModule;
import com.jiayang.imdemo.m.model.AppModule;
import com.jiayang.imdemo.utils.PreferenceTool;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.event.OnContactUpdateEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by 张 奎 on 2017-09-30 18:05.
 */

public class IMAppDeletage {

    private Application application;
    private ApiComponent apiComponent;
    private AppComponent appComponent;

    public IMAppDeletage(Application application) {
        this.application = application;
    }

    public void onCreat() {
        initInjcet();
        initHuanXin();                       // 初始化环信
        initBmob();                          // 初始化Bmob
        ToastUtils.init(application);        // 吐司初始化
        PreferenceTool.init(application);    // Preference参数
        DBUtils.initDB(application);         // DBHelper初始化

    }

    private void initInjcet() {
        appComponent = DaggerAppComponent.builder()
                .appModule(getAppModule())
                .build();


        apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .appModule(getAppModule())
                .build();
    }

    private void initBmob() {
        Bmob.initialize(application, "dad7e7f2a71b9fb045414a79c6987f97");
    }

    private void initHuanXin() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(application.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }


        //初始化
        EMClient.getInstance().init(application, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);

        initContactListener();
    }

    private void initContactListener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                // 好友请求同意
                EventBus.getDefault().post(new OnContactUpdateEvent(s ,true));
            }

            @Override
            public void onContactDeleted(String s) {
                //  被删除
                EventBus.getDefault().post(new OnContactUpdateEvent(s ,false));
            }

            @Override
            public void onContactInvited(String username, String reason) {
                // 收到邀请
                //同意或者拒绝
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(username);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onContactAgreed(String s) {
                // 好友请求被同意
            }

            @Override
            public void onContactRefused(String s) {
                // 好友请求被拒绝
            }
        });
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) application.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = application.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    public void onTerminate() {
        this.appComponent = null;
        this.apiComponent = null;
        this.appComponent = null;
    }

    private AppModule getAppModule() {
        return new AppModule(application);
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}
