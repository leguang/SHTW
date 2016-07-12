package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.laboratoryactivity.YaLiJiFragmentViewPagerFragment;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

/**
 * Created by leguang on 2016/6/9 0009.
 */
public class YaLiJiFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = YaLiJiFragmentViewPagerAdapter.class.getSimpleName();
    private String[] yalijiName = {"全部", "", ""};
    private String[] yalijiID = {"", "", ""};
    private String[] titleType = {"不合格", "合格", "有效", "无效", "已处置", "未处置"};
    private ParametersData mParametersData;
    private boolean isRegistered = false;

    public YaLiJiFragmentViewPagerAdapter(FragmentManager fm, ParametersData mParametersData) {
        super(fm);
        this.mParametersData = mParametersData;
        int yalijiCount = 1;
//        for (int i = 0; i < mEquipmentData.getData().size(); i++) {
//            if (mEquipmentData.getData().get(i).getBanhezhanminchen().contains("压力机")) {
//                yalijiName[yalijiCount] = mEquipmentData.getData().get(i).getBanhezhanminchen();
//                yalijiID[yalijiCount] = mEquipmentData.getData().get(i).getGprsbianhao();
//                yalijiCount++;
//                KLog.e(TAG, mEquipmentData.getData().get(i).getGprsbianhao());
//            }
//        }

        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }

    }

    @Override
    public Fragment getItem(int position) {
        ParametersData mParametersData = (ParametersData) this.mParametersData.clone();
        if (position <= 3) {
            mParametersData.isQualified = position + "";
            mParametersData.isReal = "";
            return YaLiJiFragmentViewPagerFragment.newInstance(mParametersData);
        } else {
            mParametersData.isQualified = "0";
            if (position == 4) {
                mParametersData.isReal = "0";
                return YaLiJiFragmentViewPagerFragment.newInstance(mParametersData);
            } else {
                mParametersData.isReal = "1";
                return YaLiJiFragmentViewPagerFragment.newInstance(mParametersData);
            }
        }
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

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.YALIJIFRAGMENT) {
                this.mParametersData = (ParametersData) mParametersData.clone();
                KLog.e(mParametersData.toString());
            }
        }
    }
}
