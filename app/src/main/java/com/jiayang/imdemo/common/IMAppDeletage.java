package com.jiayang.imdemo.common;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.jiayang.imdemo.BuildConfig;
import com.jiayang.imdemo.R;
import com.jiayang.imdemo.db.DBUtils;
import com.jiayang.imdemo.m.component.ApiComponent;
import com.jiayang.imdemo.m.component.AppComponent;
import com.jiayang.imdemo.m.component.DaggerApiComponent;
import com.jiayang.imdemo.m.component.DaggerAppComponent;
import com.jiayang.imdemo.m.model.ApiModule;
import com.jiayang.imdemo.m.model.AppModule;
import com.jiayang.imdemo.utils.PreferenceTool;
import com.jiayang.imdemo.utils.ToastUtils;
import com.jiayang.imdemo.v.activity.ChatActivity;
import com.jiayang.imdemo.v.activity.MainActivity;
import com.jiayang.imdemo.v.adapter.MessageListenerAdapter;
import com.jiayang.imdemo.v.event.OnContactUpdateEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by 张 奎 on 2017-09-30 18:05.
 */

public class IMAppDeletage {

    private Application application;
    private ApiComponent apiComponent;
    private AppComponent appComponent;

    private SoundPool mSoundPool;
    private int mDuanSound;
    private int mYuluSound;

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

        initSoundPool();
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

        // 联系人监听
        initContactListener();
        // 消息的监听
        initMessageListener();
    }

    private void initSoundPool() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        mDuanSound = mSoundPool.load(application, R.raw.duan, 1);
        mYuluSound = mSoundPool.load(application, R.raw.yulu, 1);
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

    private void initMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new MessageListenerAdapter() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                super.onMessageReceived(list);
                if (list != null && list.size() > 0) {
                    /**
                     * 1. 判断当前应用是否在后台运行
                     * 2. 如果是在后台运行，则发出通知栏
                     * 3. 如果是在后台发出长声音
                     * 4. 如果在前台发出短声音
                     */
                    if (isRuninBackground()) {
                        sendNotification(list.get(0));
                        //发出长声音
                        //参数2/3：左右喇叭声音的大小
                        mSoundPool.play(mYuluSound,1,1,0,0,1);
                    } else {
                        //发出短声音
                        mSoundPool.play(mDuanSound,1,1,0,0,1);
                    }
                    EventBus.getDefault().post(list.get(0));
                }
            }
        });
    }

    private boolean isRuninBackground() {
        ActivityManager activityManager = (ActivityManager) application.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
        if (runningTaskInfo.topActivity.getPackageName().equals(application.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    private void sendNotification(EMMessage message) {
        EMTextMessageBody messageBody = (EMTextMessageBody) message.getBody();
        NotificationManager notificationManager = (NotificationManager) application.getSystemService(NOTIFICATION_SERVICE);
        //延时意图
        /**
         * 参数2：请求码 大于1
         */
        Intent mainIntent = new Intent(application,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chatIntent = new Intent(application, ChatActivity.class);
        chatIntent.putExtra("username",message.getFrom());

        Intent[] intents = {mainIntent,chatIntent};
        PendingIntent pendingIntent = PendingIntent.getActivities(application,1,intents,PendingIntent.FLAG_UPDATE_CURRENT) ;
        @SuppressLint({"NewApi", "LocalSuppress"}) Notification notification = new Notification.Builder(application)
                .setAutoCancel(true) //当点击后自动删除
                .setSmallIcon(R.mipmap.message) //必须设置
                .setLargeIcon(BitmapFactory.decodeResource(application.getResources(),R.mipmap.default_avatar))
                .setContentTitle("您有一条新消息")
                .setContentText(messageBody.getMessage())
                .setContentInfo(message.getFrom())
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        notificationManager.notify(1,notification);
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
