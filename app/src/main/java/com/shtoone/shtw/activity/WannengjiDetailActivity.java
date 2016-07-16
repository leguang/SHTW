package com.shtoone.shtw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.YalijiDetailData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


public class WannengjiDetailActivity extends BaseActivity {
    private static final String TAG = WannengjiDetailActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private NestedScrollView mNestedScrollView;
    private StoreHouseHeader header;
    private FloatingActionButton fab;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private YalijiDetailData mYalijiDetailData;
    private TextView tv_date;
    private TextView tv_equipment;
    private TextView tv_project;
    private TextView tv_position;
    private TextView tv_testtype;
    private TextView tv_identifier;
    private TextView tv_diameter;
    private TextView tv_kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wannengji_detail);

        initView();
        initDate();
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab_wannengji_detail_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_wannengji_detail_activity);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_wannengji_detail_activity);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_wannengji_detail_activity);
        mViewPager = (ViewPager) findViewById(R.id.vp_wannengji_detail_activity);
        ptrframe = (PtrFrameLayout) findViewById(R.id.ptr_wannengji_detail_activity);
        pageStateLayout = (PageStateLayout) findViewById(R.id.psl_wannengji_detail_activity);
        tv_date = (TextView) findViewById(R.id.tv_date_wannengji_detail_activity);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment_wannengji_detail_activity);
        tv_project = (TextView) findViewById(R.id.tv_project_name_wannengji_detail_activity);
        tv_position = (TextView) findViewById(R.id.tv_position_wannengji_detail_activity);
        tv_testtype = (TextView) findViewById(R.id.tv_testtype_wannengji_detail_activity);
        tv_identifier = (TextView) findViewById(R.id.tv_identifier_wannengji_detail_activity);
        tv_diameter = (TextView) findViewById(R.id.tv_diameter_wannengji_detail_activity);
        tv_kind = (TextView) findViewById(R.id.tv_kind_wannengji_detail_activity);
    }

    private void initDate() {
        mToolbar.setTitle("XX高速 > 试验室 > 压力机 > 压力机详情");
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                Handler mHandler = new Handler();
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mNestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                    }
//                });
            }
        });

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showContent();
                getDataFromNetwork();
            }
        });

        pageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showEmpty();
                fab.hide();
                NetworkUtils.openSetting(WannengjiDetailActivity.this);
            }
        });

        header = new StoreHouseHeader(this);
        final String[] mStringList = {ConstantsUtils.DOMAIN_1, ConstantsUtils.DOMAIN_2};

        // 下拉刷新头部
        header.setTextColor(Color.BLACK);
        header.setPadding(0, DisplayUtils.dp2px(15), 0, 0);

        header.initWithString(mStringList[0]);
        // for changing string
        ptrframe.addPtrUIHandler(new PtrUIHandler() {

            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                header.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                String string = mStringList[mLoadTime % mStringList.length];
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });
        ptrframe.setHeaderView(header);
        ptrframe.addPtrUIHandler(header);
        ptrframe.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrframe.autoRefresh(true);
            }
        }, 100);
        ptrframe.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mNestedScrollView.getScrollY() == 0) {
                    return true;
                }
                return false;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                getDataFromNetwork();
                frame.refreshComplete();
            }
        });
    }

    private void getDataFromNetwork() {

        //从全局参数类中取出参数，避免太长了，看起来不方便
        String detaiID = BaseApplication.parametersData.detailID;
        detaiID = "A5F94CED-EF65-4F5A-B2C3-6CDB770587A4";
        //联网获取数据
        HttpUtils.getRequest(URL.getYalijiDetailData(detaiID), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.e(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                if (!NetworkUtils.isConnected(WannengjiDetailActivity.this)) {
                    //提示网络异常,让用户点击设置网络
                    pageStateLayout.showNetError();
                    fab.hide();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    pageStateLayout.showError();
                    fab.hide();
                }
            }
        });
    }

    protected void parseData(String response) {
        if (!TextUtils.isEmpty(response)) {
            mYalijiDetailData = new Gson().fromJson(response, YalijiDetailData.class);
            if (null != mYalijiDetailData) {
                if (mYalijiDetailData.isSuccess()) {
                    pageStateLayout.showContent();
                    fab.show();
                    setAdapter();
                } else {
                    //提示数据为空，展示空状态
                    pageStateLayout.showEmpty();
                    fab.hide();
                }
            } else {
                //提示数据解析异常，展示错误页面
                pageStateLayout.showError();
                fab.hide();
            }
        } else {
            //提示返回数据异常，展示错误页面
            pageStateLayout.showError();
            fab.hide();
        }

    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        // 设置显示数据
        tv_equipment.setText(mYalijiDetailData.getData().getShebeiname());
        tv_project.setText(mYalijiDetailData.getData().getGCMC());
        tv_position.setText(mYalijiDetailData.getData().getSGBW());
        tv_testtype.setText(mYalijiDetailData.getData().getTestName());
        tv_identifier.setText(mYalijiDetailData.getData().getSJQD());
        tv_diameter.setText(mYalijiDetailData.getData().getSJCC());
        tv_kind.setText(mYalijiDetailData.getData().getLQ());
//        mViewPager.setAdapter(new YaLiJiDetailActivityChartViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
