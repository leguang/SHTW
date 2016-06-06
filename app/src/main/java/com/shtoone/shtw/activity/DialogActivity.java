package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.utils.ToastUtils;

/**
 * Created by leguang on 2016/6/01 0031.
 */

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DialogActivity";
    private TextView start_date;
    private TextView end_date;
    private CircularProgressButton bt_search;
    private ImageView iv_cancel;
    private ParametersData parametersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
        initData();
        parametersData = new ParametersData();
        parametersData.deviceType = "0";
    }

    private void initView() {
        start_date = (TextView) findViewById(R.id.start_date_dialog);
        end_date = (TextView) findViewById(R.id.end_date_dialog);
        bt_search = (CircularProgressButton) findViewById(R.id.bt_search_dialog);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel_dialog);
    }

    private void initData() {
        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        iv_cancel.setOnClickListener(this);
        bt_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date_dialog:
                ToastUtils.showToast(this, "开始");
                break;

            case R.id.end_date_dialog:
                ToastUtils.showToast(this, "结束");
                break;

            case R.id.iv_cancel_dialog:
                ToastUtils.showToast(this, "取消");
                break;

            case R.id.bt_search_dialog:
                ToastUtils.showToast(this, "查询");
                BaseApplication.bus.post(parametersData);
                finish();
                break;
        }
    }

}
