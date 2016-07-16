package com.shtoone.shtw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.shtoone.shtw.R;


public class TestActivity extends Activity {
    private static final String TAG = TestActivity.class.getSimpleName();
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

                login_password.setErrorEnabled(false);
            }
        });
    }
}
