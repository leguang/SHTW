package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.VolleyError;
import com.dd.CircularProgressButton;
import com.google.gson.Gson;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.KeyBoardUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.URL;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private TextInputLayout login_username;
    private TextInputLayout login_password;
    private CircularProgressButton login_button;
    private UserInfoData userInfoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    private void initView() {
        login_username = (TextInputLayout) findViewById(R.id.login_username);
        login_password = (TextInputLayout) findViewById(R.id.login_password);
        login_button = (CircularProgressButton) findViewById(R.id.login_button);
    }

    private void initData() {
        login_username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_button.setProgress(0);
                if (TextUtils.isEmpty(s)) {
                    login_username.getEditText().setError("用户名不能为空");
                    login_username.setErrorEnabled(true);
                } else {
                    login_username.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        login_username.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_button.setProgress(0);
            }
        });


        login_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_button.setProgress(0);
                login_password.setErrorEnabled(true);
                if (TextUtils.isEmpty(s)) {
                    login_password.getEditText().setError("密码不能为空");
                } else {
                    login_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_password.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_button.setProgress(0);
            }
        });

        login_button.setIndeterminateProgressMode(true);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyBoardUtils.hideKeybord(v, LoginActivity.this);
                final String username = login_username.getEditText().getText().toString().trim();
                final String password = login_password.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    login_button.setProgress(0);
                    login_button.setProgress(50);
                    //联网校对密码正确后保存
                    HttpUtils.getRequest(URL.loginCheck(username, password), new HttpUtils.HttpListener() {
                        @Override
                        public void onSuccess(String response) {
                            if (!TextUtils.isEmpty(response)) {
                                userInfoData = new Gson().fromJson(response, UserInfoData.class);
                                if (null != userInfoData) {
                                    if (userInfoData.isSuccess()) {
                                        SharedPreferencesUtils.put(LoginActivity.this, "username", username);
                                        SharedPreferencesUtils.put(LoginActivity.this, "password", password);
                                        SharedPreferencesUtils.put(LoginActivity.this, "loginCheck", response);
                                        login_button.setProgress(100);
                                        jumpTo();
                                    } else {
                                        //提示用户名或密码错误
                                        login_button.setErrorText("用户名或密码错误");
                                        login_button.setProgress(-1);
                                    }
                                } else {
                                    //提示数据解析异常
                                    login_button.setErrorText("解析数据异常");
                                    login_button.setProgress(-1);
                                }
                            } else {
                                //提示返回数据异常
                                login_button.setErrorText("返回数据异常");
                                login_button.setProgress(-1);
                            }
                        }

                        @Override
                        public void onFailed(VolleyError error) {
                            //提示网络数据异常
                            login_button.setErrorText("网络异常");
                            login_button.setProgress(-1);
                        }
                    });

                } else if (TextUtils.isEmpty(username)) {
                    login_username.setError("用户名不能为空");
                } else if (TextUtils.isEmpty(password)) {
                    login_password.setError("密码不能为空");
                }
            }
        });
    }

    //进入MainActivity
    private void jumpTo() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int setContainerId() {
        return 0;
    }

    //点击空白处隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = this.getCurrentFocus();
        KeyBoardUtils.hideKeybord(v, this);
        return super.dispatchTouchEvent(ev);
    }

}
