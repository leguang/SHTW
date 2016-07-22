package com.shtoone.shtw.activity;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.SharedPreferencesUtils;


public class GuideActivity extends BaseActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();
    private Button bt_guide;
    private ViewPager mViewPager;
    private RelativeLayout rl_container;
    private int[] resouces = {R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3};
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initDate();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_guide_activity);
        rl_container = (RelativeLayout) findViewById(R.id.rl_container_guide_activity);
        bt_guide = (Button) findViewById(R.id.bt_guide_activity);
    }

    private void initDate() {
        mViewPager.setAdapter(new GuideViewPagerAdapter());
        bt_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.put(GuideActivity.this, ConstantsUtils.ISFIRSTENTRY, false);
                // 页面跳转
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                // 结束自己
                GuideActivity.this.finish();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int evaluate;
                switch (position) {
                    case 0:
                        bt_guide.setVisibility(View.GONE);
                        evaluate = (Integer) mArgbEvaluator.evaluate(positionOffset, 0XFF8080FF, 0XFF80FF80);
                        rl_container.setBackgroundColor(evaluate);
                        break;
                    case 1:
                        bt_guide.setVisibility(View.GONE);
                        evaluate = (Integer) mArgbEvaluator.evaluate(positionOffset, 0XFF80FF80, 0XFFFF8080);
                        rl_container.setBackgroundColor(evaluate);
                        break;
                    case 2:
                        bt_guide.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class GuideViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (resouces != null) {
                return resouces.length;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(GuideActivity.this, R.layout.item_viewpager_guide_activity, null);
            FrameLayout fl_container = (FrameLayout) view.findViewById(R.id.fl_container_item_viewpager_guide_activity);
            TextView tv = (TextView) view.findViewById(R.id.tv_item_viewpager_guide_activity);
            tv.setText(position + "");
//            fl_container.setBackgroundResource(resouces[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
