package com.shtoone.shtw.activity;

import android.content.Intent;
import android.graphics.RectF;
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
import zhy.com.highlight.HighLight;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private int bottomNavigationPreposition = 0;
    private HighLight mHightLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mFragments[0] = LaboratoryFragment.newInstance();
            mFragments[1] = ConcreteFragment.newInstance();
            mFragments[2] = AsphaltFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container_main_activity, 0, mFragments[0], mFragments[1], mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = findFragment(LaboratoryFragment.class);
            mFragments[1] = findFragment(ConcreteFragment.class);
            mFragments[2] = findFragment(AsphaltFragment.class);
        }

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
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
                bottomNavigationPreposition = position;
            }
        });

//        showTipMask();
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
        SharedPreferencesUtils.put(this, ConstantsUtils.LOGINCHECK, "");
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
    protected void onResume() {
        if ((Boolean) SharedPreferencesUtils.get(MainActivity.this, "isFirstGuide", true)) {
            showTipMask();
        }
        super.onResume();
    }

    private void showTipMask() {
        mHightLight = new HighLight(MainActivity.this)//
                .addHighLight(R.id.action_hierarchy, R.layout.info_gravity_right_up, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.rightMargin = 1;
                        marginInfo.topMargin = rectF.bottom;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        mHightLight = new HighLight(MainActivity.this);
                        mHightLight.addHighLight(R.id.fab_laboratory_fragment, R.layout.info_down, new HighLight.OnPosCallback() {
                            @Override
                            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                                marginInfo.rightMargin = rectF.width() / 2 + 1;
                                marginInfo.bottomMargin = bottomMargin + rectF.height();
                            }
                        }).setClickCallback(new HighLight.OnClickCallback() {
                            @Override
                            public void onClick() {
                                SharedPreferencesUtils.put(MainActivity.this, "isFirstGuide", false);
                            }
                        }).show();
                    }
                });
        mHightLight.show();
    }

}
