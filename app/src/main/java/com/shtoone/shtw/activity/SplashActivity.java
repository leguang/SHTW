package com.shtoone.shtw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.URL;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private UserInfoData userInfoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        XGPushConfig.enableDebug(this, true);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

// 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);


        //延迟执行，尽量看到闪屏页
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                initView();
                initData();
            }
        }, 2500);
    }

    private void initView() {
    }

    private void initData() {
        String username = (String) SharedPreferencesUtils.get(this, "username", "");
        String password = (String) SharedPreferencesUtils.get(this, "password", "");
        String loginCheck = (String) SharedPreferencesUtils.get(this, "loginCheck", "");
        if (!TextUtils.isEmpty(loginCheck)) {
            userInfoData = new Gson().fromJson(loginCheck, UserInfoData.class);
        }
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            //联网校对密码正确后保存
            HttpUtils.getRequest(URL.loginCheck(username, password), new HttpUtils.HttpListener() {
                @Override
                public void onSuccess(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        userInfoData = new Gson().fromJson(response, UserInfoData.class);
                        if (null != userInfoData) {
                            if (userInfoData.isSuccess()) {
                                SharedPreferencesUtils.put(SplashActivity.this, "loginCheck", response);
                                initParametersData();
                                jumpToMain();
                            } else {
                                //提示用户名或密码错误,有可能用户在Web端改了密码
                                jumpToLogin();
                            }
                        } else {
                            //提示数据解析异常，与硬件和系统有关的问题，几乎不可能出现
                            jumpToLogin();
                        }
                    } else {
                        //提示返回数据异常，丢包的情况，几乎不会出现
                        jumpToLogin();
                    }
                }

                @Override
                public void onFailed(VolleyError error) {
                    //提示网络数据异常，无网络
                    jumpToLogin();
                }
            });

        } else {
            jumpToLogin();
        }


    }

    //进入LoginActivity
    private void jumpToLogin() {
        Boolean isFirstentry = (Boolean) SharedPreferencesUtils.get(this, "firstentry", true);
        Intent intent;
        if (isFirstentry) {
            intent = new Intent(this, GuideActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    //进入MainActivity
    private void jumpToMain() {

        Boolean isFirstentry = (Boolean) SharedPreferencesUtils.get(this, "isFirstEntry", true);
        Intent intent;
        if (isFirstentry) {
            intent = new Intent(this, GuideActivity.class);

        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void initParametersData() {
        BaseApplication.parametersData.userGroupID = userInfoData.getDepartId();
    }

    @Override
    public void onBackPressed() {
        //解决闪屏页按返回键还进入主界面，干脆禁止在闪屏页按返回
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }
}
