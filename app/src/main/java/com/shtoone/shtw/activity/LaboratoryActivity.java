package com.shtoone.shtw.activity;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.EventData;
import com.shtoone.shtw.fragment.laboratoryactivity.LaboratoryStatisticFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.WannengjiFragment;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

public class LaboratoryActivity extends BaseActivity {
    private static final String TAG = LaboratoryActivity.class.getSimpleName();
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private int bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private String itemFromSG;
    private FrameLayout fl_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);
        itemFromSG = getIntent().getStringExtra("SG");

        if (savedInstanceState == null) {
            mFragments[0] = YaLiJiFragment.newInstance();
            mFragments[1] = WannengjiFragment.newInstance();
            mFragments[2] = LaboratoryStatisticFragment.newInstance();
            int showPosition = 0;
//            if ("SG".equals(BaseApplication.mUserInfoData.getType())) {
//                switch (itemFromSG) {
//                    case "yaliji":
//                        showPosition = 0;
//                        break;
//
//                    case "wannengji":
//                        showPosition = 1;
//                        break;
//
//                    case "statistic":
//                        showPosition = 2;
//                        break;
//                }
//            }
            loadMultipleRootFragment(R.id.fl_container_laboratory_activity, showPosition, mFragments[0], mFragments[1], mFragments[2]);
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
        fl_container = (FrameLayout) findViewById(R.id.fl_container_laboratory_activity);
    }

    public void initData() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.yaliji, R.drawable.ic_yaliji, R.color.base_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.wannengji, R.drawable.ic_wannengji, R.color.base_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.statistic, R.drawable.ic_statistic, R.color.base_color);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
//        bottomNavigation.setColored(true);
//        bottomNavigation.setForceTint(true);
        bottomNavigation.setForceTitlesDisplay(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, final boolean wasSelected) {

                showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
                bottomNavigationPreposition = position;
                if (wasSelected) {
                    BaseApplication.bus.post(new EventData(position));
                }

                fl_container.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!wasSelected && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            int cx = (fl_container.getLeft() + fl_container.getRight()) / 2;
                            int cy = fl_container.getBottom();
                            int radius = Math.max(fl_container.getWidth(), fl_container.getHeight());
                            Animator mAnimator = ViewAnimationUtils.createCircularReveal(fl_container, cx, cy, 0, radius);
                            mAnimator.setDuration(300);
                            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                            mAnimator.start();
                        }
                    }
                });
            }
        });

        int currentItem = 0;
        if ("SG".equals(BaseApplication.mUserInfoData.getType())) {
            switch (itemFromSG) {
                case "yaliji":
                    currentItem = 0;
                    break;

                case "wannengji":
                    currentItem = 1;
                    break;

                case "statistic":
                    currentItem = 2;
                    break;
            }
        }

        bottomNavigation.setCurrentItem(currentItem);
    }
}
