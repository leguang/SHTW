package com.shtoone.shtw.activity;

import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.EventData;
import com.shtoone.shtw.fragment.concreteactivity.ConcreteStatisticFragment;
import com.shtoone.shtw.fragment.concreteactivity.MaterialStatisticFragment;
import com.shtoone.shtw.fragment.concreteactivity.OverproofFragment;
import com.shtoone.shtw.fragment.concreteactivity.ProduceQueryFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

public class ConcreteActivity extends BaseActivity {
    private static final String TAG = ConcreteActivity.class.getSimpleName();
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete);

        if (savedInstanceState == null) {
            mFragments[0] = ProduceQueryFragment.newInstance();
            mFragments[1] = OverproofFragment.newInstance();
            mFragments[2] = ConcreteStatisticFragment.newInstance();
            mFragments[3] = MaterialStatisticFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_concrete_activity, 0, mFragments[0], mFragments[1], mFragments[2], mFragments[3]);
        } else {
            mFragments[0] = findFragment(ProduceQueryFragment.class);
            mFragments[1] = findFragment(OverproofFragment.class);
            mFragments[2] = findFragment(ConcreteStatisticFragment.class);
            mFragments[3] = findFragment(MaterialStatisticFragment.class);
        }

        initView();
        initData();
    }

    private void initView() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_concrete_activity);
    }

    public void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.produce_query, R.drawable.ic_favorites, R.color.base_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.overproof, R.drawable.ic_nearby, R.color.base_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.statistic, R.drawable.ic_friends, R.color.base_color);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.material_statistic, R.drawable.ic_friends, R.color.base_color);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
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
                if (wasSelected) {
                    BaseApplication.bus.post(new EventData(position));
                }
            }
        });
    }
}
