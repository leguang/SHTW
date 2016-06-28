package com.shtoone.shtw.fragment.laboratoryactivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.fragment.base.BaseFragment;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by leguang on 2016/6/9 0031.
 */
public class LaboratoryStatisticFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = LaboratoryStatisticFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private StoreHouseHeader header;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private BarChart mBarChart;
    private Typeface mTf;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Button bt_search;
    private boolean isStartDateTime;

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    public static LaboratoryStatisticFragment newInstance() {
        return new LaboratoryStatisticFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laboratory_statistic, container, false);
        initView(view);
        initDate();
        return view;
    }

    private void initView(View view) {
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date_laboratory_statistic_fragment);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date_laboratory_statistic_fragment);
        bt_search = (Button) view.findViewById(R.id.bt_search_laboratory_statistic_fragment);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_laboratory_statistic_fragment);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nsv_laboratory_statistic_fragment);
        ptrframe = (PtrFrameLayout) view.findViewById(R.id.ptr_laboratory_statistic_fragment);
        pageStateLayout = (PageStateLayout) view.findViewById(R.id.psl_laboratory_statistic_fragment);
        mBarChart = (BarChart) view.findViewById(R.id.barchart_laboratory_statistic_fragment);
    }

    private void initDate() {
        mToolbar.setTitle("XX高速 > 试验室 > 综合统计");
        initToolbarBackNavigation(mToolbar);
        initToolbarMenu(mToolbar);

        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStartDateTime();
            }
        });

        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEndDateTime();
            }
        });

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromNetwork();
            }
        });

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showContent();
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

        setData(12, 50);
    }

    private void getDataFromNetwork() {

        //从全局参数类中取出参数，避免太长了，看起来不方便
        String detaiID = BaseApplication.parametersData.detailID;
        detaiID = "7AAFB8D1-AFFC-4AAA-9F9A-19324061AA54";
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
//        if (!TextUtils.isEmpty(response)) {
//            mYalijiDetailData = new Gson().fromJson(response, YalijiDetailData.class);
//            if (null != mYalijiDetailData) {
//                if (mYalijiDetailData.isSuccess()) {
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

    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        // 设置显示数据
    }

    private void setData(int count, float range) {


        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);

        mBarChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        mTf = Typeface.createFromAsset(_mActivity.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });


        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 12]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1;

        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals1);
            mBarChart.getData().setXVals(xVals);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet");
            set1.setBarSpacePercent(35f);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTf);

            mBarChart.setData(data);
        }
    }

    public class MyYAxisValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;

        public MyYAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(value) + " $";
        }
    }

    private void setStartDateTime() {
        isStartDateTime = true;
        showDatePicker();
    }

    private void setEndDateTime() {
        isStartDateTime = false;
        showDatePicker();
    }


    private void showDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.vibrate(true);
        dpd.dismissOnPause(false);
        dpd.setAccentColor(getResources().getColor(R.color.base_color));
        dpd.show(_mActivity.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) _mActivity.getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String monthString = (++monthOfYear) < 10 ? "0" + (monthOfYear) : "" + (monthOfYear);
        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String dateString = year + "-" + monthString + "-" + dayString + " ";
        if (isStartDateTime) {
            tv_start_date.setText(dateString);
        } else {
            tv_end_date.setText(dateString);
        }
    }
}
