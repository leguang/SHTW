package com.shtoone.shtw;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

/**
 * Created by leguang on 2016/5/20 0031.
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";
    public static BaseApplication application;
    public static Context context;
    public static final Bus bus = new Bus();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        // 添加功能：程序异常关闭1s之后重新启动
    }
}
