package com.shtoone.shtw.fragment.laboratoryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.adapter.YaLiJiFragmentViewPagerAdapter;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class YaLiJiFragment extends BaseFragment {
    private static final String TAG = YaLiJiFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private AppBarLayout mAppBarLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private boolean isRegistered = false;
    private ParametersData mParametersData;

    public static YaLiJiFragment newInstance() {
        return new YaLiJiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yaliji, container, false);
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab_yaliji_fragment);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_yaliji_fragment);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_yaliji_fragment);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout_yaliji_fragment);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_yaliji_fragment);
//        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_yaliji_fragment);
//        pageStateLayout.showLoading();
    }

    private void initData() {
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.fromTo = ConstantsUtils.YALIJIFRAGMENT;
        KLog.e(mParametersData.toString());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantsUtils.PARAMETERS, YaLiJiFragment.this.mParametersData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (0 == verticalOffset) {
                    BaseApplication.isExpand = true;
                } else {
                    BaseApplication.isExpand = false;
                }
            }
        });
//        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pageStateLayout.showContent();
//                getDataFromNetwork(mParametersData);
//            }
//        });
//
//        pageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pageStateLayout.showEmpty();
//                NetworkUtils.openSetting(_mActivity);
//            }
//        });

        StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
        sb.append(getString(R.string.laboratory) + " > ");
        sb.append(getString(R.string.yaliji)).trimToSize();
        mToolbar.setTitle(sb.toString());
        initToolbarBackNavigation(mToolbar);
        initToolbarMenu(mToolbar);
        setAdapter();
//        getDataFromNetwork(mParametersData);
    }

//    private void getDataFromNetwork(ParametersData mParametersData) {
//
//        //联网获取数据
//        HttpUtils.getRequest(URL.getEquipment(mParametersData.userGroupID), new HttpUtils.HttpListener() {
//            @Override
//            public void onSuccess(String response) {
//                KLog.json(response);
//                parseData(response);
//            }
//
//            @Override
//            public void onFailed(VolleyError error) {
//                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
//                if (!NetworkUtils.isConnected(_mActivity)) {
//                    //提示网络异常,让用户点击设置网络
//                    pageStateLayout.showNetError();
//                } else {
//                    //服务器异常，展示错误页面，点击刷新
//                    pageStateLayout.showError();
//                }
//            }
//        });
//    }
//
//    protected void parseData(String response) {
//        if (!TextUtils.isEmpty(response)) {
//            mEquipmentData = new Gson().fromJson(response, EquipmentData.class);
//            if (null != mEquipmentData) {
//                if (mEquipmentData.isSuccess()) {
//                    pageStateLayout.showContent();
//                    setAdapter();
//                } else {
//                    //提示数据为空，展示空状态
//                    pageStateLayout.showEmpty();
//                }
//            } else {
//                //提示数据解析异常，展示错误页面
//                pageStateLayout.showError();
//            }
//        } else {
//            //提示返回数据异常，展示错误页面
//            pageStateLayout.showError();
//        }
//
//    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        mViewPager.setAdapter(new YaLiJiFragmentViewPagerAdapter(getChildFragmentManager(), mParametersData));
//        mViewPager.getCurrentItem();
        mTabLayout.setupWithViewPager(mViewPager);
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

    @Override
    public void onResume() {
        super.onResume();
        //返回到看见此fragment时，fab显示
        fab.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //防止屏幕旋转后重画时fab显示
        fab.hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.bus.unregister(this);
    }
}
