package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.fragment.laboratoryactivity.ViewPagerFragment;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    String[] mTitles = new String[]{"推荐", "热门", "收藏", "收藏", "收藏", "收藏", "收藏", "收藏", "收藏", "收藏"};
    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerFragment.newInstance();
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
