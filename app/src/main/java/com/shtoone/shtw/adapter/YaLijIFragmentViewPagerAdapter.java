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
    private String[] titleType = {"不合格", "合格", "有效", "无效", "已处置", "未处置"};

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
        return YaLiJiFragmentViewPagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        if (null != titleType) {
            return titleType.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        if (null != mEquipmentData && mEquipmentData.isSuccess()) {
//            return yalijiName[position];
//        }
        if (null != titleType) {
            return titleType[position];
        }
        return null;
    }
}
