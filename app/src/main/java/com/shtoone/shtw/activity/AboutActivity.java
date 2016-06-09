package com.shtoone.shtw.activity;

import android.os.Bundle;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;


public class AboutActivity extends BaseActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        initView();
        initDate();
    }

    @Override
    protected int setContainerId() {
        return 0;
    }

    private void initDate() {
    }

    private void initView() {
    }
}
