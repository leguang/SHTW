package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.shtoone.shtw.bean.EquipmentData;
import com.shtoone.shtw.fragment.laboratoryactivity.WannengjiFragmentViewPagerFragment;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class WannengjiFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "WannengjiFragmentViewPagerAdapter";
    private EquipmentData mEquipmentData;
    private String[] wannengjiName = {"全部", "", ""};
    private String[] wannengjiID = {"", "", ""};

    public WannengjiFragmentViewPagerAdapter(FragmentManager fm, EquipmentData mEquipmentData) {
        super(fm);
        this.mEquipmentData = mEquipmentData;
        int yalijiCount = 1;
        for (int i = 0; i < mEquipmentData.getData().size(); i++) {
            if (mEquipmentData.getData().get(i).getBanhezhanminchen().contains("万能机")) {
                wannengjiName[yalijiCount] = mEquipmentData.getData().get(i).getBanhezhanminchen();
                wannengjiID[yalijiCount] = mEquipmentData.getData().get(i).getGprsbianhao();
                yalijiCount++;
                Log.e(TAG, mEquipmentData.getData().get(i).getGprsbianhao());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return WannengjiFragmentViewPagerFragment.newInstance(wannengjiID[position]);
    }

    @Override
    public int getCount() {
        if (null != mEquipmentData && mEquipmentData.isSuccess()) {
            return wannengjiName.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != mEquipmentData && mEquipmentData.isSuccess()) {
            return wannengjiName[position];
        }
        return null;
    }
}
