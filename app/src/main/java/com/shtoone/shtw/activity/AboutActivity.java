package com.shtoone.shtw.activity;

import android.os.Bundle;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;

/**
 * Created by leguang on 2016/6/11.
 */

public class AboutActivity extends BaseActivity {
    private static final String TAG = AboutActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
        initDate();
    }


    private void initDate() {
    }

    private void initView() {
    }
}
