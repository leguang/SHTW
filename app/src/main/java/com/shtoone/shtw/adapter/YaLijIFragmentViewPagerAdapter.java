package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.bean.EquipmentData;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragmentViewPagerFragment;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class YaLiJiFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "YaLiJiFragVPAdapter";
    private EquipmentData mEquipmentData;
    private String[] yaliji = {"全部", "", ""};

    public YaLiJiFragmentViewPagerAdapter(FragmentManager fm, EquipmentData mEquipmentData) {
        super(fm);
        this.mEquipmentData = mEquipmentData;
        int yalijiCount = 1;
        for (int i = 0; i < mEquipmentData.getData().size(); i++) {
            if (mEquipmentData.getData().get(i).getBanhezhanminchen().contains("压力机")) {
                yaliji[yalijiCount++] = mEquipmentData.getData().get(i).getBanhezhanminchen();
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return YaLiJiFragmentViewPagerFragment.newInstance();
    }

    @Override
    public int getCount() {

        if (null != mEquipmentData && mEquipmentData.isSuccess()) {

            return yaliji.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != mEquipmentData && mEquipmentData.isSuccess()) {
            return yaliji[position];
        }
        return null;
    }
}
