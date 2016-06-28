package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.fragment.laboratoryactivity.LaboratoryStatisticFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.WannengjiFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class LaboratoryActivity extends BaseActivity {
    private static final String TAG = LaboratoryActivity.class.getSimpleName();
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);

        if (savedInstanceState == null) {
            mFragments[0] = YaLiJiFragment.newInstance();
            mFragments[1] = WannengjiFragment.newInstance();
            mFragments[2] = LaboratoryStatisticFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container_laboratory_activity, 0, mFragments[0], mFragments[1], mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = findFragment(YaLiJiFragment.class);
            mFragments[1] = findFragment(WannengjiFragment.class);
            mFragments[2] = findFragment(LaboratoryStatisticFragment.class);
        }

        initView();
        initData();
    }

    private void initView() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_laboratory_activity);
    }

    public void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.yaliji, R.drawable.ic_favorites, R.color.base_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.wannengji, R.drawable.ic_nearby, R.color.base_color);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.statistic, R.drawable.ic_friends, R.color.base_color);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item4);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
//        bottomNavigation.setColored(true);
//        bottomNavigation.setForceTint(true);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {

                showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
                bottomNavigationPreposition = position;
            }
        });

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
