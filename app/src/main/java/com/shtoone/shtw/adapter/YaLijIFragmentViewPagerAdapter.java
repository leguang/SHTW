package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.bean.EquipmentData;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragmentViewPagerFragment;
import com.socks.library.KLog;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class YaLiJiFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = YaLiJiFragmentViewPagerAdapter.class.getSimpleName();
    private EquipmentData mEquipmentData;
    private String[] yalijiName = {"全部", "", ""};
    private String[] yalijiID = {"", "", ""};

    public YaLiJiFragmentViewPagerAdapter(FragmentManager fm, EquipmentData mEquipmentData) {
        super(fm);
        this.mEquipmentData = mEquipmentData;
        int yalijiCount = 1;
        for (int i = 0; i < mEquipmentData.getData().size(); i++) {
            if (mEquipmentData.getData().get(i).getBanhezhanminchen().contains("压力机")) {
                yalijiName[yalijiCount] = mEquipmentData.getData().get(i).getBanhezhanminchen();
                yalijiID[yalijiCount] = mEquipmentData.getData().get(i).getGprsbianhao();
                yalijiCount++;
                KLog.e(TAG, mEquipmentData.getData().get(i).getGprsbianhao());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return YaLiJiFragmentViewPagerFragment.newInstance(yalijiID[position]);
    }

    @Override
    public int getCount() {

        if (null != mEquipmentData && mEquipmentData.isSuccess()) {

            return yalijiName.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != mEquipmentData && mEquipmentData.isSuccess()) {
            return yalijiName[position];
        }
        return null;
    }
}
