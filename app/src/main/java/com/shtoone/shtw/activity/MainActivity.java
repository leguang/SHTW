package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.mainactivity.AsphaltFragment;
import com.shtoone.shtw.fragment.mainactivity.ConcreteFragment;
import com.shtoone.shtw.fragment.mainactivity.LaboratoryFragment;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView_main_activity);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_main_activity);
    }

    public void initToolBar(Toolbar toolbar) {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.laboratory, R.drawable.ic_favorites, R.color.base_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.concrete, R.drawable.ic_nearby, R.color.base_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.asphalt, R.drawable.ic_friends, R.color.base_color);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
//        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
//        bottomNavigation.setColored(true);
//        bottomNavigation.setForceTint(true);
//        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        LaboratoryFragment fragment0 = findFragment(LaboratoryFragment.class);
                        if (fragment0 == null) {
                            popTo(LaboratoryFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(LaboratoryFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment0, SupportFragment.SINGLETASK);
                        }

                        break;

                    case 1:

                        ConcreteFragment fragment1 = findFragment(ConcreteFragment.class);
                        if (fragment1 == null) {
                            popTo(LaboratoryFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(ConcreteFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment1, SupportFragment.SINGLETASK);
                        }

                        break;

                    case 2:

                        AsphaltFragment fragment2 = findFragment(AsphaltFragment.class);
                        if (fragment2 == null) {
                            popTo(LaboratoryFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(AsphaltFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment2, SupportFragment.SINGLETASK);
                        }
                        break;
                }
            }
        });
        bottomNavigation.setCurrentItem(0);
    }

    @Override
    protected int setContainerId() {
        return R.id.fl_container_main_activity;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(0, 0, 0, 0);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.message_drawer_main_activity) {

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
        SharedPreferencesUtils.put(this, ConstantsUtils.LOGINCHECK, "");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void JumpToAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void JumpToVersionActivity() {
        Intent intent = new Intent(this, VersionActivity.class);
        startActivity(intent);
    }

}
