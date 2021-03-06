package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.ConcreteMainActivityData;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ConcreteMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = ConcreteMainActivity.class.getSimpleName();
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toggle;
    private TextView tv_username;
    private TextView tv_phone_number;
    private Toolbar mToolbar;
    private LinearLayout llNavHeader;
    private CardView cv_yaliji, cv_wannengji, cv_statistic;
    private TextView tv_day, tv_week, tv_month, tv_primary, tv_middle, tv_high;
    private ConcreteMainActivityData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_main);
        initView();
        initData();
    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView_concrete_main_activity);
        mNavigationView.setNavigationItemSelectedListener(this);
        llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_concrete_main_activity);

        cv_yaliji = (CardView) findViewById(R.id.cv_produce_query_concrete_main_activity);
        cv_wannengji = (CardView) findViewById(R.id.cv_overproof_concrete_main_activity);
        cv_statistic = (CardView) findViewById(R.id.cv_statistic_concrete_main_activity);

        tv_day = (TextView) findViewById(R.id.tv_day_concrete_main_activity);
        tv_week = (TextView) findViewById(R.id.tv_week_concrete_main_activity);
        tv_month = (TextView) findViewById(R.id.tv_month_concrete_main_activity);

        tv_primary = (TextView) findViewById(R.id.tv_primary_concrete_main_activity);
        tv_middle = (TextView) findViewById(R.id.tv_middle_concrete_main_activity);
        tv_high = (TextView) findViewById(R.id.tv_high_concrete_main_activity);

    }

    public void initData() {
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        if (null != mToolbar && null != BaseApplication.mUserInfoData && !TextUtils.isEmpty(BaseApplication.mUserInfoData.getDepartName())) {
            StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
            sb.append(getString(R.string.banhezhan)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }

//        BaseApplication.backupParametersData = (ParametersData) BaseApplication.parametersData.clone();
        if (null != BaseApplication.mUserInfoData) {
            if (!TextUtils.isEmpty(BaseApplication.mUserInfoData.getUserFullName())) {
                tv_username.setText("用户：" + BaseApplication.mUserInfoData.getUserFullName());
            }
            if (!TextUtils.isEmpty(BaseApplication.mUserInfoData.getUserPhoneNum())) {
                tv_phone_number.setText("电话：" + BaseApplication.mUserInfoData.getUserPhoneNum());
            }
        }

        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                mDrawer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(ConcreteMainActivity.this, "点击……");
                    }
                }, 500);
            }
        });

        cv_yaliji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConcreteMainActivity.this, ConcreteActivity.class);
                intent.putExtra("SG", "producequery");
                startActivity(intent);
            }
        });

        cv_wannengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConcreteMainActivity.this, ConcreteActivity.class);
                intent.putExtra("SG", "overproof");
                startActivity(intent);
            }
        });

        cv_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConcreteMainActivity.this, ConcreteActivity.class);
                intent.putExtra("SG", "statistic");
                startActivity(intent);
            }
        });

        refresh();
    }

    @Override
    public String createRefreshULR() {
        return URL.getLibSGMain(BaseApplication.mDepartmentData.departmentID);
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
//                mPageStateLayout.showError();
            }
            if (jsonObject.optBoolean("success")) {
                data = new Gson().fromJson(response, ConcreteMainActivityData.class);
                if (null != data) {
                    if (data.isSuccess()) {
//                        mPageStateLayout.showContent();
                        setAdapter();

                    } else {
                        //提示数据为空，展示空状态
//                        mPageStateLayout.showEmpty();
                    }
                } else {
                    //提示数据解析异常，展示错误页面
//                    mPageStateLayout.showError();
                }
            } else {
                //提示数据为空，展示空状态
//                mPageStateLayout.showEmpty();
            }
        } else {
            //提示返回数据异常，展示错误页面
//            mPageStateLayout.showError();
        }

    }

    private void setAdapter() {
        if (TextUtils.isEmpty(data.getData().getDayCount()) && TextUtils.isEmpty(data.getData().getWeekCount())) {
            tv_day.setText("0");
            tv_week.setText("0");
            tv_month.setText("0");

            tv_primary.setText("0");
            tv_middle.setText("0");
            tv_high.setText("0");
        } else {
            tv_day.setText(data.getData().getDayCount());
            tv_week.setText(data.getData().getWeekCount());
            tv_month.setText(data.getData().getMonthCount());

            tv_primary.setText(data.getData().getPrimary());
            tv_middle.setText(data.getData().getMiddle());
            tv_high.setText(data.getData().getHigh());
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.message_drawer_main_activity) {
            JumpToMessageActivity();
        } else if (id == R.id.logout_drawer_main_activity) {
            JumpToLoginActivity();
        } else if (id == R.id.about_drawer_main_activity) {
            JumpToAboutActivity();
        } else if (id == R.id.version_drawer_main_activity) {
            JumpToVersionActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void JumpTo() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    private void JumpToLoginActivity() {
        //清除已存的用户信息
        SharedPreferencesUtils.put(this, ConstantsUtils.USERNAME, "");
        SharedPreferencesUtils.put(this, ConstantsUtils.PASSWORD, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void JumpToMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    private void JumpToAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void JumpToVersionActivity() {
        Intent intent = new Intent(this, VersionActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
