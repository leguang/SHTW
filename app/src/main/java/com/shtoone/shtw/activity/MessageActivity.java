package com.shtoone.shtw.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.MessageActivityViewPagerAdapter;
import com.shtoone.shtw.utils.ToastUtils;

/**
 * Created by leguang on 2016/6/11.
 */

public class MessageActivity extends BaseActivity {
    private static final String TAG = MessageActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initView();
        initDate();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_message_activity);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_message_activity);
        mViewPager = (ViewPager) findViewById(R.id.vp_message_activity);
    }

    private void initDate() {
        mToolbar.setTitle("消息中心");
        initToolbarBackNavigation(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_hierarchy);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        ToastUtils.showToast(MessageActivity.this, "清除……");
                        break;
                }
                return true;
            }
        });

        mViewPager.setAdapter(new MessageActivityViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
