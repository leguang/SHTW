package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragmentViewPagerFragment;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class YaLiJiFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "YaLiJiFragmentViewPagerAdapter";

    String[] mTitles = new String[]{"全部", "1标压力机", "3标压力机"};

    public YaLiJiFragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return YaLiJiFragmentViewPagerFragment.newInstance();
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
