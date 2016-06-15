package com.shtoone.shtw.fragment.laboratoryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.adapter.YaLiJiFragmentViewPagerAdapter;
import com.shtoone.shtw.fragment.base.BaseFragment;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class YaLiJiFragment extends BaseFragment {
    private static final String TAG = "YaLiJiFragment";
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

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
        fab = (FloatingActionButton) view.findViewById(R.id.fab_yaliji_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                startActivity(intent);
            }
        });

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_yaliji_fragment);
        mToolbar.setTitle("XX高速 > 试验室 > 压力机");
        initToolbarBackNavigation(mToolbar);
        initToolbarMenu(mToolbar);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout_yaliji_fragment);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_yaliji_fragment);
        mViewPager.setAdapter(new YaLiJiFragmentViewPagerAdapter(getChildFragmentManager()));
//        mViewPager.getCurrentItem();
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
