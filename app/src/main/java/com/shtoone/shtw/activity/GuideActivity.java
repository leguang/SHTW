package com.shtoone.shtw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.utils.SharedPreferencesUtils;


public class GuideActivity extends BaseActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();
    private Button bt_guide;
    private ViewPager vp;
    private int[] resouces = {R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initDate();
    }

    private void initDate() {
        vp.setAdapter(new GuideViewPagerAdapter());
        bt_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.put(GuideActivity.this, "isFirstEntry", false);
                // 页面跳转
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                // 结束自己
                GuideActivity.this.finish();
            }
        });
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp_guide_activity);
        bt_guide = (Button) findViewById(R.id.bt_guide_activity);
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
            bt_guide.setVisibility(position == resouces.length - 1 ? View.VISIBLE : View.INVISIBLE);
            View view = View.inflate(GuideActivity.this, R.layout.item_viewpager_guide_activity, null);
            ImageView iv_pager = (ImageView) view.findViewById(R.id.iv_item_viewpager_guide_activity);
            iv_pager.setBackgroundResource(resouces[position]);
            container.addView(iv_pager);
            return iv_pager;
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
