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

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;
import com.shtoone.shtw.utils.ToastUtils;

public class LaboratoryMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = LaboratoryMainActivity.class.getSimpleName();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory_main);
        initView();
        initData();
    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView_laboratory_main_activity);
        mNavigationView.setNavigationItemSelectedListener(this);
        llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_laboratory_main_activity);
        cv_yaliji = (CardView) findViewById(R.id.cv_yaliji_laboratory_main_activity);
        cv_wannengji = (CardView) findViewById(R.id.cv_wannengji_laboratory_main_activity);
        cv_statistic = (CardView) findViewById(R.id.cv_statistic_laboratory_main_activity);

    }

    public void initData() {
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        if (null != mToolbar && null != BaseApplication.mUserInfoData && !TextUtils.isEmpty(BaseApplication.mUserInfoData.getDepartName())) {
            StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
            sb.append(getString(R.string.laboratory)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }

        BaseApplication.backupParametersData = (ParametersData) BaseApplication.parametersData.clone();
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
                        ToastUtils.showToast(LaboratoryMainActivity.this, "点击……");
                    }
                }, 500);
            }
        });

        cv_yaliji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaboratoryMainActivity.this, LaboratoryActivity.class);
                intent.putExtra("SG", "yaliji");
                startActivity(intent);
            }
        });

        cv_wannengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaboratoryMainActivity.this, LaboratoryActivity.class);
                intent.putExtra("SG", "wannengji");
                startActivity(intent);
            }
        });

        cv_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaboratoryMainActivity.this, LaboratoryActivity.class);
                intent.putExtra("SG", "statistic");
                startActivity(intent);
            }
        });
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
