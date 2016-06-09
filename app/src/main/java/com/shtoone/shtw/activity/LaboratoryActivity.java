package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.laboratoryactivity.DisqualificationFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.LaboratoryStatisticFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.WanNengJiFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class LaboratoryActivity extends BaseActivity {

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);
        initView();
        initData();
    }

    private void initView() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_laboratory_activity);
    }

    public void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.yaliji, R.drawable.ic_favorites, R.color.base_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.wannengji, R.drawable.ic_nearby, R.color.base_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.disqualification, R.drawable.ic_friends, R.color.base_color);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.statistic, R.drawable.ic_friends, R.color.base_color);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
//        bottomNavigation.setColored(true);
//        bottomNavigation.setForceTint(true);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        YaLiJiFragment fragment0 = findFragment(YaLiJiFragment.class);
                        if (fragment0 == null) {
                            popTo(YaLiJiFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(YaLiJiFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment0, SupportFragment.SINGLETASK);
                        }

                        break;

                    case 1:

                        WanNengJiFragment fragment1 = findFragment(WanNengJiFragment.class);
                        if (fragment1 == null) {
                            popTo(YaLiJiFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(WanNengJiFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment1, SupportFragment.SINGLETASK);
                        }

                        break;

                    case 2:

                        DisqualificationFragment fragment2 = findFragment(DisqualificationFragment.class);
                        if (fragment2 == null) {
                            popTo(YaLiJiFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(DisqualificationFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment2, SupportFragment.SINGLETASK);
                        }
                        break;

                    case 3:

                        LaboratoryStatisticFragment fragment3 = findFragment(LaboratoryStatisticFragment.class);
                        if (fragment3 == null) {
                            popTo(YaLiJiFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(LaboratoryStatisticFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(fragment3, SupportFragment.SINGLETASK);
                        }
                        break;
                }
            }
        });
        bottomNavigation.setCurrentItem(0);
    }

    @Override
    protected int setContainerId() {
        return R.id.fl_container_laboratory_activity;
    }


    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(0, 0, 0, 0);
    }


    private void JumpTo() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
