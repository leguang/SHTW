package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.YaLiJiDetailActivityChartViewPagerAdapter;


public class YaLiJiDetailActivity extends BaseActivity {
    private static final String TAG = "YaLiJiDetailActivity";
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaliji_detail);

        initView();
        initDate();
    }

    @Override
    protected int setContainerId() {
        return 0;
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar_yaliji_detail_activity);
        mToolbar.setTitle("XX高速 > 试验室 > 压力机 > 压力机详情页");
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_yaliji_detail_activity);
        mViewPager = (ViewPager) findViewById(R.id.vp_yaliji_detail_activity);
        mViewPager.setAdapter(new YaLiJiDetailActivityChartViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initDate() {


    }


}
