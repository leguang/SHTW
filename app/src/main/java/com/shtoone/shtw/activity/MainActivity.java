package com.shtoone.shtw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.HunNingTuBHZFragment;
import com.shtoone.shtw.fragment.LaboratoryFragment;
import com.shtoone.shtw.fragment.LiQingBHZFragment;

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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.hunningtu_banhezhan, R.drawable.ic_nearby, R.color.base_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.liqing_banhezhan, R.drawable.ic_friends, R.color.base_color);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#3F51B5"));
//        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(Color.parseColor("#ffffff"));
        bottomNavigation.setInactiveColor(Color.parseColor("#808080"));
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

                        HunNingTuBHZFragment fragment1 = findFragment(HunNingTuBHZFragment.class);
                        if (fragment1 == null) {
                            popTo(LaboratoryFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(HunNingTuBHZFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment1, SupportFragment.SINGLETASK);
                        }

                        break;

                    case 2:

                        LiQingBHZFragment fragment2 = findFragment(LiQingBHZFragment.class);
                        if (fragment2 == null) {
                            popTo(LaboratoryFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(LiQingBHZFragment.newInstance());
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

//            Fragment topFragment = getTopFragment();
//            // 主页的Fragment
//            if (!(topFragment instanceof LaboratoryFragment)) {
//                bottomNavigation.setCurrentItem(0);
//                return;
//            }
//            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(0, 0, 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
