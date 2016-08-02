package com.shtoone.shtw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.shtoone.shtw.R;
import com.shtoone.shtw.utils.AnimationUtils;


public class Test1Activity extends Activity {
    private static final String TAG = Test1Activity.class.getSimpleName();
    private TextInputLayout login_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        login_password = (TextInputLayout) findViewById(R.id.login_password);

        findViewById(R.id.test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_password.setError("");
                login_password.setError("错了");
                login_password.setErrorEnabled(true);
            }
        });

        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AnimationUtils.startActivity(Test1Activity.this, MessageActivity.class, view, R.color.colorPrimary);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 添加返回过渡动画.
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
