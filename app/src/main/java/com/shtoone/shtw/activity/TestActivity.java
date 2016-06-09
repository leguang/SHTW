package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.view.View;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.NetworkUtils;


public class TestActivity extends BaseActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        final PageStateLayout loadingLayout = (PageStateLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.btn_show_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.showContent();
            }
        });
        findViewById(R.id.btn_show_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.showError();
            }
        });
        findViewById(R.id.btn_show_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.showEmpty();
            }
        });

        findViewById(R.id.btn_show_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.showLoading();
            }
        });

        findViewById(R.id.btn_show_net_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.showNetError();
            }
        });

        loadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.showLoading();
            }
        });

        loadingLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtils.openSetting(TestActivity.this);
            }
        });


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
