package com.shtoone.shtw.fragment.concreteactivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DialogActivity;
import com.shtoone.shtw.adapter.MaterialStatisticFragmentRecyclerViewAdapter;
import com.shtoone.shtw.bean.MaterialStatisticFragmentData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by leguang on 2016/7/20 0020.
 */
public class MaterialStatisticFragment extends BaseLazyFragment {
    private static final String TAG = MaterialStatisticFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private StoreHouseHeader header;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private Typeface mTf;
    private MaterialStatisticFragmentRecyclerViewAdapter mAdapter;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private boolean isRegistered = false;
    private BarChart mBarChart0, mBarChart1;
    private MaterialStatisticFragmentData data;
    private ParametersData mParametersData;
    private Gson mGson;
    private LinearLayoutManager mLinearLayoutManager;

    public static MaterialStatisticFragment newInstance() {
        return new MaterialStatisticFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        View view = inflater.inflate(R.layout.fragment_material_statistic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_material_statistic_fragment);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv_material_statistic_fragment);
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_material_statistic_fragment);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_material_statistic_fragment);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_material_statistic_fragment);
        pageStateLayout.showLoading();
        mBarChart0 = (BarChart) view.findViewById(R.id.barchart0_material_statistic_fragment);
        mBarChart1 = (BarChart) view.findViewById(R.id.barchart1_material_statistic_fragment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_material_statistic_fragment);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        mGson = new Gson();
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.fromTo = ConstantsUtils.MATERIALSTATISTICFRAGMENT;
        StringBuffer sb = new StringBuffer(BaseApplication.mUserInfoData.getDepartName() + " > ");
        sb.append(getString(R.string.concrete) + " > ");
        sb.append(getString(R.string.material_statistic)).trimToSize();
        mToolbar.setTitle(sb.toString());
        initToolbarBackNavigation(mToolbar);
//        initToolbarMenu(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_mActivity, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantsUtils.PARAMETERS, mParametersData);
                intent.putExtras(bundle);
                //Activity共享元素切换版本适配
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                startActivity(intent);
//                } else {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, fab, getString(R.string.transition_dialog));
//                    startActivity(intent, options.toBundle());
//                }
            }
        });

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showLoading();
                ptrframe.autoRefresh(true);
            }
        });

        pageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showEmpty();
                NetworkUtils.openSetting(_mActivity);
            }
        });

        header = new StoreHouseHeader(_mActivity);
        final String[] mStringList = {ConstantsUtils.DOMAIN_1, ConstantsUtils.DOMAIN_2};

        // 下拉刷新头部
        header.setTextColor(Color.BLACK);
        header.setPadding(0, DisplayUtils.dp2px(15), 0, 0);
        header.initWithString(mStringList[0]);
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
        String userGroupID = mParametersData.userGroupID;
        String startDateTime = mParametersData.startDateTime;
        String endDateTime = mParametersData.endDateTime;
        String equipmentID = mParametersData.equipmentID;

        //联网获取数据
        HttpUtils.getRequest(URL.getBHZCailiaoyongliang(userGroupID, startDateTime, endDateTime, equipmentID), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.e(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                if (!NetworkUtils.isConnected(_mActivity)) {
                    //提示网络异常,让用户点击设置网络
                    pageStateLayout.showNetError();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    pageStateLayout.showError();
                }
            }
        });
    }

    protected void parseData(String response) {
        if (!TextUtils.isEmpty(response)) {
            data = mGson.fromJson(response, MaterialStatisticFragmentData.class);
            if (null != data && data.getData().size() > 0) {
                if (data.isSuccess()) {
                    pageStateLayout.showContent();
                    setViewData();
                } else {
                    //提示数据为空，展示空状态
                    pageStateLayout.showEmpty();
                }
            } else {
                //提示数据解析异常，展示错误页面
                pageStateLayout.showError();
            }
        } else {
            //提示返回数据异常，展示错误页面
            pageStateLayout.showError();
        }
    }


    private void setViewData() {
        ArrayList<String> x = new ArrayList<String>();
        ArrayList<BarEntry> y0 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> y1 = new ArrayList<BarEntry>();
        List<MaterialStatisticFragmentData.DataBean> mList = data.getData();
        for (int i = 0; i < mList.size(); i++) {
            x.add(mList.get(i).getName());
            y0.add(new BarEntry(Float.parseFloat(mList.get(i).getShiji()), i));
            y1.add(new BarEntry(Float.parseFloat(mList.get(i).getPeibi()), i));
        }

        setChart(mBarChart0);
        setChart(mBarChart1);

        setChartData(mBarChart0, x, y0);
        setChartData(mBarChart1, x, y1);

        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new MaterialStatisticFragmentRecyclerViewAdapter(_mActivity, data.getData()));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);
    }

    private void setChartData(BarChart mBarChart, ArrayList<String> x, ArrayList<BarEntry> y) {
        BarDataSet mBarDataSet;
        mBarDataSet = new BarDataSet(y, "材料类型");
        mBarDataSet.setBarSpacePercent(35f);
        mBarDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(mBarDataSet);
        BarData data = new BarData(x, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
        mBarChart.setData(data);
    }

    private void setChart(BarChart mBarChart) {
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.setDescription("");
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.animateXY(2000, 2000);
        mBarChart.setDrawGridBackground(false);

        mTf = Typeface.createFromAsset(_mActivity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(0);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    private void changeReadedState(View view) {
        //此处可以做一些修改点击过的item的样式，方便用户看出哪些已经点击查看过
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.MATERIALSTATISTICFRAGMENT) {
                this.mParametersData = (ParametersData) mParametersData.clone();
                ptrframe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrframe.autoRefresh(true);
                    }
                }, 300);
                mNestedScrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP);
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
