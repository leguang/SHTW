package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiDetailActivityChartFragment;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class YaLiJiDetailActivityChartViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "YaLiJiDetailActivityChartViewPagerAdapter";
    String[] mTitles = new String[]{"1111", "2222", "33333"};

    public YaLiJiDetailActivityChartViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return YaLiJiDetailActivityChartFragment.newInstance();
    }


    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
