package com.shtoone.shtw.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.UserInfoData;
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
    private String detaiID;
    private ImageView iv_photo_select;
    private ImageView iv_camera_select;
    private ImageView iv_album_select;
    private TextInputLayout et_handle_person;
    private TextInputLayout et_handle_time;
    private TextInputLayout et_handle_reason;
    private TextInputLayout et_handle_way;
    private TextInputLayout et_handle_result;
    private Button bt_submit;
    private Button bt_reset;
    private LinearLayout ll_camera_album;
    private UserInfoData mUserInfoData;
    private String handlePerson;
    private String handleTime;
    private String handleReason;
    private String handleWay;
    private String handleResult;
    private Bitmap bitmap;

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
//        tv_date = (TextView) findViewById(R.id.tv_date_wannengji_detail_activity);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment_wannengji_detail_activity);
        tv_project = (TextView) findViewById(R.id.tv_project_name_wannengji_detail_activity);
        tv_position = (TextView) findViewById(R.id.tv_position_wannengji_detail_activity);
        tv_testtype = (TextView) findViewById(R.id.tv_testtype_wannengji_detail_activity);
        tv_identifier = (TextView) findViewById(R.id.tv_identifier_wannengji_detail_activity);
        tv_diameter = (TextView) findViewById(R.id.tv_diameter_wannengji_detail_activity);
        tv_kind = (TextView) findViewById(R.id.tv_kind_wannengji_detail_activity);

        //处置部分
        iv_photo_select = (ImageView) findViewById(R.id.iv_photo_select_yaliji_detail_activity);
        iv_camera_select = (ImageView) findViewById(R.id.iv_camera_select_yaliji_detail_activity);
        iv_album_select = (ImageView) findViewById(R.id.iv_album_select_yaliji_detail_activity);
        ll_camera_album = (LinearLayout) findViewById(R.id.ll_camera_album_yaliji_detail_activity);
        et_handle_person = (TextInputLayout) findViewById(R.id.et_handle_person_yaliji_detail_activity);
        et_handle_time = (TextInputLayout) findViewById(R.id.et_handle_time_yaliji_detail_activity);
        et_handle_reason = (TextInputLayout) findViewById(R.id.et_handle_reason_yaliji_detail_activity);
        et_handle_way = (TextInputLayout) findViewById(R.id.et_handle_way_yaliji_detail_activity);
        et_handle_result = (TextInputLayout) findViewById(R.id.et_handle_result_yaliji_detail_activity);
        et_handle_person.getEditText().setInputType(InputType.TYPE_NULL);
        et_handle_time.getEditText().setInputType(InputType.TYPE_NULL);
        bt_submit = (Button) findViewById(R.id.bt_submit_yaliji_detail_activity);
        bt_reset = (Button) findViewById(R.id.bt_reset_yaliji_detail_activity);
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
