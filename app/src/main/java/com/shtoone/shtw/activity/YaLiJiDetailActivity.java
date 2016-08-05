package com.shtoone.shtw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.YaLiJiDetailActivityChartViewPagerAdapter;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.bean.YalijiDetailData;
import com.shtoone.shtw.bean.YalijiFragmentViewPagerFragmentRecyclerViewItemData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


public class YaLiJiDetailActivity extends BaseActivity {
    private static final String TAG = YaLiJiDetailActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private NestedScrollView mNestedScrollView;
    private StoreHouseHeader header;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private YalijiDetailData mYalijiDetailData;
    private TextView tv_datetime;
    private TextView tv_equipment;
    private TextView tv_project;
    private TextView tv_position;
    private TextView tv_testtype;
    private TextView tv_design_strength;
    private TextView tv_size;
    private TextView tv_age;
    private TextView tv_central_value;
    private TextInputLayout et_handle_reason;
    private Button bt_submit;
    private Button bt_reset;
    private UserInfoData mUserInfoData;
    private String handleReason;
    private CardView cv_handle;
    private YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean mDataBean;
//    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaliji_detail);

        initView();
        initDate();
    }

    private void initView() {
        mDataBean = (YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean) getIntent().getSerializableExtra("yalijidetail");
        mToolbar = (Toolbar) findViewById(R.id.toolbar_yaliji_detail_activity);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_yaliji_detail_activity);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_yaliji_detail_activity);
        mViewPager = (ViewPager) findViewById(R.id.vp_yaliji_detail_activity);
        ptrframe = (PtrFrameLayout) findViewById(R.id.ptr_yaliji_detail_activity);
        pageStateLayout = (PageStateLayout) findViewById(R.id.psl_yaliji_detail_activity);
        pageStateLayout.showLoading();
        tv_datetime = (TextView) findViewById(R.id.tv_datetime_yaliji_detail_activity);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment_yaliji_detail_activity);
        tv_project = (TextView) findViewById(R.id.tv_project_name_yaliji_detail_activity);
        tv_position = (TextView) findViewById(R.id.tv_position_yaliji_detail_activity);
        tv_testtype = (TextView) findViewById(R.id.tv_testtype_yaliji_detail_activity);
        tv_design_strength = (TextView) findViewById(R.id.tv_design_strength_yaliji_detail_activity);
        tv_size = (TextView) findViewById(R.id.tv_size_yaliji_detail_activity);
        tv_age = (TextView) findViewById(R.id.tv_age_yaliji_detail_activity);
        tv_central_value = (TextView) findViewById(R.id.tv_central_value_yaliji_detail_activity);
        cv_handle = (CardView) findViewById(R.id.cv_handle_yaliji_detail_activity);
        et_handle_reason = (TextInputLayout) findViewById(R.id.et_handle_reason_yaliji_detail_activity);
        bt_submit = (Button) findViewById(R.id.bt_submit_yaliji_detail_activity);
        bt_reset = (Button) findViewById(R.id.bt_reset_yaliji_detail_activity);
    }

    private void initDate() {
        mUserInfoData = BaseApplication.mUserInfoData;
        if ("不合格".equals(mDataBean.getPDJG()) || "无效".equals(mDataBean.getPDJG())) {
            cv_handle.setVisibility(View.VISIBLE);
            if (mUserInfoData.getQuanxian().isSyschaobiaoReal()) {
                bt_submit.setEnabled(true);
                bt_reset.setEnabled(true);
            }
        }
        setToolbarTitle();
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);

        pageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showLoading();
                getDataFromNetwork();
            }
        });

        pageStateLayout.setOnNetErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageStateLayout.showEmpty();
                NetworkUtils.openSetting(YaLiJiDetailActivity.this);
            }
        });

        et_handle_reason.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    et_handle_reason.getEditText().setError("处置原因不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        header = new StoreHouseHeader(this);
        final String[] mStringList = {ConstantsUtils.DOMAIN_1, ConstantsUtils.DOMAIN_2};

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


        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReason = et_handle_reason.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(handleReason)) {
                    //弹出对话框，确定提交
                    new MaterialDialog.Builder(YaLiJiDetailActivity.this)
                            .title("确认")
                            .content("请问您确定填写无误并提交吗？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    MaterialDialog progressDialog = new MaterialDialog.Builder(YaLiJiDetailActivity.this)
                                            .title("提交")
                                            .content("正在提交中，请稍等……")
                                            .progress(true, 0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
                                    submit(progressDialog);
                                }
                            })
                            .negativeText("放弃")
                            .show();
                } else {
                    et_handle_reason.getEditText().setError("处置原因不能为空");
                }
            }
        });

        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框,确定重置
                new MaterialDialog.Builder(YaLiJiDetailActivity.this)
                        .title("确认")
                        .content("请问您确定要重置吗？那样您就要重新填写哟……")
                        .positiveText("确定")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //提交到服务器
                                ToastUtils.showToast(YaLiJiDetailActivity.this, "重置");
                                et_handle_reason.getEditText().setText("");
                                handleReason = "";
                                et_handle_reason.setFocusable(false);
                            }
                        })
                        .negativeText("放弃")
                        .show();
            }
        });
    }

    private void submit(final MaterialDialog progressDialog) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("SYJID", mDataBean.getSYJID());
        paramsMap.put("chaobiaoyuanyin", handleReason);

        HttpUtils.postRequest(URL.SYS_CHAOBIAO_DO_URL, paramsMap, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();
                KLog.json(response);
                if (!TextUtils.isEmpty(response)) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.showToast(YaLiJiDetailActivity.this, "解析异常");
                    }

                    if (jsonObject.optBoolean("success")) {

                        ToastUtils.showToast(YaLiJiDetailActivity.this, "上传成功");
                    } else {
                        ToastUtils.showToast(YaLiJiDetailActivity.this, "上传失败");
                    }

                } else {
                    ToastUtils.showToast(YaLiJiDetailActivity.this, "上传异常");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                progressDialog.dismiss();
                if (!NetworkUtils.isConnected(YaLiJiDetailActivity.this)) {
                    //提示网络异常,让用户点击设置网络，
                    View view = YaLiJiDetailActivity.this.getWindow().getDecorView();
                    Snackbar.make(view, "当前网络已断开！", Snackbar.LENGTH_LONG)
                            .setAction("设置网络", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 跳转到系统的网络设置界面
                                    NetworkUtils.openSetting(YaLiJiDetailActivity.this);
                                }
                            }).show();
                } else {
                    //服务器异常，展示错误页面，点击刷新
                    ToastUtils.showToast(YaLiJiDetailActivity.this, "服务器异常");
                }
            }
        });
    }

    private void getDataFromNetwork() {
        //联网获取数据
        HttpUtils.getRequest(URL.getYalijiDetailData(mDataBean.getSYJID()), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                if (!NetworkUtils.isConnected(YaLiJiDetailActivity.this)) {
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
            mYalijiDetailData = new Gson().fromJson(response, YalijiDetailData.class);
            if (null != mYalijiDetailData) {
                if (mYalijiDetailData.isSuccess()) {
                    pageStateLayout.showContent();
                    setAdapter();
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

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {
        // 设置显示数据
        tv_datetime.setText(mYalijiDetailData.getData().getSYRQ());
        tv_equipment.setText(mYalijiDetailData.getData().getShebeiname());
        tv_project.setText(mYalijiDetailData.getData().getGCMC());
        tv_position.setText(mYalijiDetailData.getData().getSGBW());
        tv_testtype.setText(mYalijiDetailData.getData().getTestName());
        tv_design_strength.setText(mYalijiDetailData.getData().getSJQD());
        tv_size.setText(mYalijiDetailData.getData().getSJCC());
        tv_age.setText(mYalijiDetailData.getData().getLQ());
        tv_central_value.setText(mYalijiDetailData.getData().getQDDBZ());
        mViewPager.setAdapter(new YaLiJiDetailActivityChartViewPagerAdapter(getSupportFragmentManager(), mYalijiDetailData));
        mTabLayout.setupWithViewPager(mViewPager);
        if (!TextUtils.isEmpty(mYalijiDetailData.getData().getChuli())) {
            et_handle_reason.getEditText().setText(mYalijiDetailData.getData().getChuli());
        }
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.laboratory) + " > ");
            sb.append(getString(R.string.yaliji) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }
}
