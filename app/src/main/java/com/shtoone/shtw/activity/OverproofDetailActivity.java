package com.shtoone.shtw.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.adapter.OverproofDetailActivityRecyclerViewAdapter;
import com.shtoone.shtw.bean.OverproofDetailActivityData;
import com.shtoone.shtw.bean.OverproofFragmentViewPagerFragmentListData;
import com.shtoone.shtw.bean.UserInfoData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.DisplayUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.KeyBoardUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


public class OverproofDetailActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = OverproofDetailActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private NestedScrollView mNestedScrollView;
    private StoreHouseHeader header;
    private PageStateLayout pageStateLayout;
    private PtrFrameLayout ptrframe;
    private OverproofDetailActivityData data;
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private RecyclerView mRecyclerView;
    private String detaiID;
    private ImageView iv_photo_select;
    private ImageView iv_camera_select;
    private ImageView iv_album_select;
    private TextInputLayout et_handle_person;
    private TextInputLayout et_handle_time;
    private TextInputLayout et_handle_reason;
    private TextInputLayout et_handle_way;
    private TextInputLayout et_handle_result;
    private Button bt_handle_submit;
    private Button bt_handle_reset;
    private TextInputLayout et_examine_person;
    private TextInputLayout et_examine_result;
    private TextInputLayout et_examine_approve;
    private TextInputLayout et_confirm_date;
    private TextInputLayout et_approve_date;
    private Button bt_examine_submit;
    private Button bt_examine_reset;
    private CardView cv_examine;
    private CardView cv_handle;
    private LinearLayout ll_camera_album;
    private UserInfoData mUserInfoData;
    private String handlePerson;
    private String handleTime;
    private String handleReason;
    private String handleWay;
    private String handleResult;
    private Bitmap bitmap;
    private OverproofDetailActivityRecyclerViewAdapter mAdapter;
    private OverproofFragmentViewPagerFragmentListData.DataBean mDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overproof_detail);

        initView();
        initDate();

        //为了解耦，不应该如老版本一样，把是否合格的判别字段传过来，然后判断哪些UI该显示
        //应该直接在进入这个页面通过detailID查询返回的json中包含判别
    }

    private void initView() {
        mUserInfoData = BaseApplication.mUserInfoData;
        mDataBean = (OverproofFragmentViewPagerFragmentListData.DataBean) getIntent().getSerializableExtra("overproofdetail");
        mToolbar = (Toolbar) findViewById(R.id.toolbar_overproof_detail_activity);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_overproof_detail_activity);
        ptrframe = (PtrFrameLayout) findViewById(R.id.ptr_overproof_detail_activity);
        pageStateLayout = (PageStateLayout) findViewById(R.id.psl_overproof_detail_activity);
        pageStateLayout.showLoading();

        tv0 = (TextView) findViewById(R.id.tv0_overproof_detail_activity);
        tv1 = (TextView) findViewById(R.id.tv1_overproof_detail_activity);
        tv2 = (TextView) findViewById(R.id.tv2_overproof_detail_activity);
        tv3 = (TextView) findViewById(R.id.tv3_overproof_detail_activity);
        tv4 = (TextView) findViewById(R.id.tv4_overproof_detail_activity);
        tv5 = (TextView) findViewById(R.id.tv5_overproof_detail_activity);
        tv6 = (TextView) findViewById(R.id.tv6_overproof_detail_activity);
        tv7 = (TextView) findViewById(R.id.tv7_overproof_detail_activity);
        tv8 = (TextView) findViewById(R.id.tv8_overproof_detail_activity);
        tv9 = (TextView) findViewById(R.id.tv9_overproof_detail_activity);
        tv10 = (TextView) findViewById(R.id.tv10_overproof_detail_activity);
        tv11 = (TextView) findViewById(R.id.tv11_overproof_detail_activity);
        tv12 = (TextView) findViewById(R.id.tv12_overproof_detail_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_overproof_detail_activity);
        //处置部分
        cv_handle = (CardView) findViewById(R.id.cv_handle_overproof_detail_activity);
        iv_photo_select = (ImageView) findViewById(R.id.iv_photo_select_overproof_detail_activity);
        iv_camera_select = (ImageView) findViewById(R.id.iv_camera_select_overproof_detail_activity);
        iv_album_select = (ImageView) findViewById(R.id.iv_album_select_overproof_detail_activity);
        ll_camera_album = (LinearLayout) findViewById(R.id.ll_camera_album_overproof_detail_activity);
        et_handle_person = (TextInputLayout) findViewById(R.id.et_handle_person_overproof_detail_activity);
        et_handle_time = (TextInputLayout) findViewById(R.id.et_handle_time_overproof_detail_activity);
        et_handle_reason = (TextInputLayout) findViewById(R.id.et_handle_reason_overproof_detail_activity);
        et_handle_way = (TextInputLayout) findViewById(R.id.et_handle_way_overproof_detail_activity);
        et_handle_result = (TextInputLayout) findViewById(R.id.et_handle_result_overproof_detail_activity);
        et_handle_person.getEditText().setInputType(InputType.TYPE_NULL);
        et_handle_time.getEditText().setInputType(InputType.TYPE_NULL);
        bt_handle_submit = (Button) findViewById(R.id.bt_handle_submit_overproof_detail_activity);
        bt_handle_reset = (Button) findViewById(R.id.bt_handle_reset_overproof_detail_activity);


        //审批部分
        cv_examine = (CardView) findViewById(R.id.cv_examine_overproof_detail_activity);
        et_examine_person = (TextInputLayout) findViewById(R.id.et_examine_person_overproof_detail_activity);
        et_examine_result = (TextInputLayout) findViewById(R.id.et_examine_result_overproof_detail_activity);
        et_examine_approve = (TextInputLayout) findViewById(R.id.et_examine_approve_overproof_detail_activity);
        et_confirm_date = (TextInputLayout) findViewById(R.id.et_confirm_time_overproof_detail_activity);
        et_approve_date = (TextInputLayout) findViewById(R.id.et_approve_time_overproof_detail_activity);
        bt_examine_submit = (Button) findViewById(R.id.bt_examine_submit_overproof_detail_activity);
        bt_examine_reset = (Button) findViewById(R.id.bt_examine_reset_overproof_detail_activity);


    }

    private void initDate() {

        if (null != mUserInfoData) {
            StringBuffer sb = new StringBuffer(mUserInfoData.getDepartName() + " > ");
            sb.append(getString(R.string.banhezhan) + " > ");
            sb.append(getString(R.string.overproof) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);

        et_handle_time.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showDatePicker();
                        break;
                }
                return true;
            }
        });

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
                NetworkUtils.openSetting(OverproofDetailActivity.this);
            }
        });

        // 下拉刷新头部
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

        iv_photo_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = (ll_camera_album.getLeft() + view.getRight()) / 2;
                    int cy = (ll_camera_album.getTop() + view.getBottom()) / 2;
                    int radius = Math.max(view.getWidth(), ll_camera_album.getHeight());
                    Animator mAnimator = ViewAnimationUtils.createCircularReveal(ll_camera_album, cx, cy, 0, radius);
                    mAnimator.setDuration(500);
                    mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    mAnimator.addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            ll_camera_album.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            iv_photo_select.setVisibility(View.INVISIBLE);
                        }
                    });
                    mAnimator.start();
                } else {
                    iv_photo_select.setVisibility(View.GONE);
                    ll_camera_album.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_camera_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, ConstantsUtils.CAMERA);
            }
        });

        iv_album_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, ConstantsUtils.ALBUM);
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

        et_handle_way.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    et_handle_way.getEditText().setError("处置方式不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_handle_result.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    et_handle_result.getEditText().setError("处置结果不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bt_handle_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyBoardUtils.hideKeybord(view, OverproofDetailActivity.this);
                handlePerson = et_handle_person.getEditText().getText().toString().trim();
                handleTime = et_handle_time.getEditText().getText().toString().trim();
                handleReason = et_handle_reason.getEditText().getText().toString().trim();
                handleWay = et_handle_way.getEditText().getText().toString().trim();
                handleResult = et_handle_result.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(handlePerson) && !TextUtils.isEmpty(handleTime) && !TextUtils.isEmpty(handleReason) && !TextUtils.isEmpty(handleWay) && !TextUtils.isEmpty(handleResult)) {
                    //弹出对话框，确定提交
                    new MaterialDialog.Builder(OverproofDetailActivity.this)
                            .title("确认")
                            .content("请问您确定填写无误并提交吗？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //提交到服务器
                                    ToastUtils.showToast(OverproofDetailActivity.this, "提交");

                                    new MaterialDialog.Builder(OverproofDetailActivity.this)
                                            .title("提交")
                                            .content("正在提交中，请稍等……")
                                            .progress(true, 0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
//                                    submit();
                                }
                            })
                            .negativeText("放弃")
                            .show();
                } else {
                    if (TextUtils.isEmpty(handlePerson)) {
                        et_handle_person.getEditText().setError("处置人不能为空");
                    } else if (TextUtils.isEmpty(handleTime)) {
                        et_handle_time.getEditText().setError("处置时间不能为空");
                    } else if (TextUtils.isEmpty(handleReason)) {
                        et_handle_reason.getEditText().setError("处置原因不能为空");
                    } else if (TextUtils.isEmpty(handleWay)) {
                        et_handle_way.getEditText().setError("处置方式不能为空");
                    } else if (TextUtils.isEmpty(handleResult)) {
                        et_handle_result.getEditText().setError("处置结果不能为空");
                    }
                }
            }
        });

        bt_handle_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框,确定重置
                new MaterialDialog.Builder(OverproofDetailActivity.this)
                        .title("确认")
                        .content("请问您确定要重置吗？那样您就要重新填写哟……")
                        .positiveText("确定")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //提交到服务器
                                ToastUtils.showToast(OverproofDetailActivity.this, "重置");
                                et_handle_reason.getEditText().setText("");
                                handleReason = "";
                                et_handle_reason.setFocusable(false);
                                et_handle_way.getEditText().setText("");
                                handleWay = "";
                                et_handle_result.getEditText().setText("");
                                handleResult = "";
                                bitmap = null;
                                iv_photo_select.setImageResource(R.drawable.ic_camera_album);
                            }
                        })
                        .negativeText("放弃")
                        .show();
            }
        });
    }

//    private void submit() {
//        String person = null, time = null, reason = null, way = null, result = null;
//        try {
//            person = URLEncoder.encode(handlePerson, "utf-8");
//            time = URLEncoder.encode(handleTime, "utf-8");
//            reason = URLEncoder.encode(handleReason, "utf-8");
//            way = URLEncoder.encode(handleWay, "utf-8");
//            result = URLEncoder.encode(handleResult, "utf-8");
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//
////        final String url = APIUtil.get("SC_DAICHUZHIBAOJING_POST").replace("%1", xxid + "")
////                .replace("%2", reason).replace("%3", type).replace("%4", result)
////                .replace("%5", person).replace("%6", time);
//
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == 1) {
//                    Toast.makeText(SC_chaobiaochaxunXqActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
//                    setResult(1);
//                    finish();
//                } else {
//                    Toast.makeText(SC_chaobiaochaxunXqActivity.this, "上传失败！", Toast.LENGTH_LONG).show();
//                }
//            }
//        };
//        new Thread(new Runnable() {
//            public void run() {
//                boolean flag = uploadPic(url); //返回为 true 表示上传成功 ； false 上传失败
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (flag) {
//                    handler.sendEmptyMessage(1);  // 上传成功 发送消息到 handler 关闭详情页并提示上传成功
//                } else {
//                    handler.sendEmptyMessage(2);  // 上传失败 则什么都不做 停留在此页面
//                }
//            }
//        }).start();
//    }


    private void getDataFromNetwork() {
        //联网获取数据
        HttpUtils.getRequest(URL.getOverproofDetailData(mDataBean.getXinxibianhao()), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(TAG, response);
                parseData(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
                if (!NetworkUtils.isConnected(OverproofDetailActivity.this)) {
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
            data = new Gson().fromJson(response, OverproofDetailActivityData.class);
            if (null != data) {
                if (data.isSuccess()) {
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
        tv0.setText(data.getHeadMsg().getGujifangshu());//数量
        tv1.setText(data.getHeadMsg().getBanhezhanminchen());//拌合站名称
        tv2.setText(data.getHeadMsg().getGongdanhao());//工单号
        tv3.setText(data.getHeadMsg().getChaozuozhe());//操作者
        tv4.setText(data.getHeadMsg().getChuliaoshijian());//出料时间
        tv5.setText(data.getHeadMsg().getJiaobanshijian());//搅拌时长
        tv6.setText(data.getHeadMsg().getGongchengmingcheng());//工程名称
        tv7.setText(data.getHeadMsg().getSigongdidian());//地点里程
        tv8.setText(data.getHeadMsg().getJiaozuobuwei());//浇筑部位
        tv9.setText(data.getHeadMsg().getShuinipingzhong());//水泥品牌
        tv10.setText(data.getHeadMsg().getQiangdudengji());//强度等级
        tv11.setText(data.getHeadMsg().getWaijiajipingzhong());//外加剂品牌
        tv12.setText(data.getHeadMsg().getPeifanghao());//施工配比编号

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置动画与适配器
        SlideInLeftAnimationAdapter mSlideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mAdapter = new OverproofDetailActivityRecyclerViewAdapter(this, data.getData()));
        mSlideInLeftAnimationAdapter.setFirstOnly(true);
        mSlideInLeftAnimationAdapter.setDuration(500);
        mSlideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator(.5f));
        ScaleInAnimationAdapter mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mSlideInLeftAnimationAdapter);
        mScaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(mScaleInAnimationAdapter);


        //设置处置部分是否显示
        if (mUserInfoData.getQuanxian().isSyschaobiaoReal()) {
            cv_handle.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(data.getHeadMsg().getChuliren())) {
                et_handle_person.getEditText().setText(handlePerson = mUserInfoData.getUserFullName());
            }
            if (TextUtils.isEmpty(data.getHeadMsg().getChulishijian())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                et_handle_time.getEditText().setText(sdf.format(Calendar.getInstance().getTime()));
            }

            if (TextUtils.isEmpty(data.getHeadMsg().getWentiyuanyin())) {
                et_handle_reason.getEditText().setText("");
            }

            if (TextUtils.isEmpty(data.getHeadMsg().getChulifangshi())) {
                et_handle_way.getEditText().setText("");

            }

            if (TextUtils.isEmpty(data.getHeadMsg().getChulijieguo())) {
                et_handle_result.getEditText().setText("");
            }
        }

        //设置审批部分是否显示
        if (mUserInfoData.getQuanxian().isHntchaobiaoSp()) {
            cv_examine.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(data.getHeadMsg().getShenpiren())) {
                et_examine_person.getEditText().setText("");
            }

            if (TextUtils.isEmpty(data.getHeadMsg().getJianliresult())) {
                et_examine_result.getEditText().setText("");
            }

            if (TextUtils.isEmpty(data.getHeadMsg().getJianlishenpi())) {
                et_examine_approve.getEditText().setText("");
            }

            if (TextUtils.isEmpty(data.getHeadMsg().getConfirmdate())) {
                et_confirm_date.getEditText().setText("");
            }

            if (TextUtils.isEmpty(data.getHeadMsg().getShenpidate())) {
                et_approve_date.getEditText().setText("");
            }
        }

    }

    private void showDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.vibrate(true);
        dpd.dismissOnPause(false);
        dpd.setAccentColor(Color.parseColor("#3F51B5"));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.setAccentColor(Color.parseColor("#3F51B5"));
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String timeString = hourString + ":" + minuteString + ":" + secondString;
        handleTime = handleTime + timeString;
        et_handle_time.getEditText().setText(handleTime);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String monthString = (++monthOfYear) < 10 ? "0" + (monthOfYear) : "" + (monthOfYear);
        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String dateString = year + "-" + monthString + "-" + dayString + " ";
        handleTime = dateString;
        showTimePicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == ConstantsUtils.CAMERA) { // 表示是从相机拍照页返回
            // 判断内存卡是否可用
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                KLog.e("SD卡不可用");
                return;
            }

            //对 返回的 bitmap 进行对应的保存操作
            String photoName = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Toast.makeText(this, photoName, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");

            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();
            String fileName = "/sdcard/myImage/" + photoName;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (Exception e2) {
                }
            }
        } else if (requestCode == ConstantsUtils.ALBUM) { // 表示是从相册选择图片返回
            Uri uri = data.getData(); //得到图片 uri
            ContentResolver resolver = getContentResolver(); //处理器
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri); //  将对应 uri 通过处理器转化为 bitmap
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            ll_camera_album.setVisibility(View.GONE);
            iv_photo_select.setVisibility(View.VISIBLE);
            iv_photo_select.setImageBitmap(bitmap);
        }
    }
}
