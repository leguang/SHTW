package com.shtoone.shtw.fragment.laboratoryactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.R;
import com.shtoone.shtw.adapter.YaLijIFragmentViewPagerAdapter;
import com.shtoone.shtw.fragment.base.BaseFragment;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class YaLiJiFragment extends BaseFragment {

    public static YaLiJiFragment newInstance() {
        return new YaLiJiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yaliji, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_yaliji_fragment);
        mToolbar.setTitle("XX高速 > 试验室 > 压力机");
        initToolbarBackNavigation(mToolbar);
        initToolbarMenu(mToolbar);
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout_yaliji_fragment);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.vp_yaliji_fragment);
        mViewPager.setAdapter(new YaLijIFragmentViewPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
