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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import me.yokeyword.fragmentation.anim.FragmentAnimator;


public class YaLiJiDetailActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = YaLiJiDetailActivity.class.getSimpleName();
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
    private TextView tv_design_strength;
    private TextView tv_size;
    private TextView tv_age;
    private TextView tv_central_value;
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
    private CardView cv_handle;
    private Bitmap bitmap;
    private YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean mDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaliji_detail);

        initView();
        initDate();

        //为了解耦，不应该如老版本一样，把是否合格的判别字段传过来，然后判断哪些UI该显示
        //应该直接在进入这个页面通过detailID查询返回的json中包含判别
    }

    private void initView() {
        mDataBean = (YalijiFragmentViewPagerFragmentRecyclerViewItemData.DataBean) getIntent().getSerializableExtra("yalijidetail");
        fab = (FloatingActionButton) findViewById(R.id.fab_yaliji_detail_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_yaliji_detail_activity);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_yaliji_detail_activity);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nsv_yaliji_detail_activity);
        mViewPager = (ViewPager) findViewById(R.id.vp_yaliji_detail_activity);
        ptrframe = (PtrFrameLayout) findViewById(R.id.ptr_yaliji_detail_activity);
        pageStateLayout = (PageStateLayout) findViewById(R.id.psl_yaliji_detail_activity);
        pageStateLayout.showLoading();
//        tv_date = (TextView) findViewById(R.id.tv_date_yaliji_detail_activity);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment_yaliji_detail_activity);
        tv_project = (TextView) findViewById(R.id.tv_project_name_yaliji_detail_activity);
        tv_position = (TextView) findViewById(R.id.tv_position_yaliji_detail_activity);
        tv_testtype = (TextView) findViewById(R.id.tv_testtype_yaliji_detail_activity);
        tv_design_strength = (TextView) findViewById(R.id.tv_design_strength_yaliji_detail_activity);
        tv_size = (TextView) findViewById(R.id.tv_size_yaliji_detail_activity);
        tv_age = (TextView) findViewById(R.id.tv_age_yaliji_detail_activity);
        tv_central_value = (TextView) findViewById(R.id.tv_central_value_yaliji_detail_activity);

        //处置部分
        cv_handle = (CardView) findViewById(R.id.cv_handle_yaliji_detail_activity);
        if (BaseApplication.mUserInfoData.getQuanxian().isSyschaobiaoReal()) {
            if ("不合格".equals(mDataBean.getPDJG()) || "无效".equals(mDataBean.getPDJG())) {
                cv_handle.setVisibility(View.VISIBLE);
            }
        }
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
        mUserInfoData = BaseApplication.mUserInfoData;

        if (null != mUserInfoData) {
            StringBuffer sb = new StringBuffer(mUserInfoData.getDepartName() + " > ");
            sb.append(getString(R.string.laboratory) + " > ");
            sb.append(getString(R.string.yaliji) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            mToolbar.setTitle(sb.toString());
            et_handle_person.getEditText().setText(handlePerson = mUserInfoData.getUserFullName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            et_handle_time.getEditText().setText(sdf.format(Calendar.getInstance().getTime()));
        }
        initToolbarBackNavigation(mToolbar);
        setSupportActionBar(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

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
                fab.hide();
                NetworkUtils.openSetting(YaLiJiDetailActivity.this);
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

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyBoardUtils.hideKeybord(view, YaLiJiDetailActivity.this);
                handlePerson = et_handle_person.getEditText().getText().toString().trim();
                handleTime = et_handle_time.getEditText().getText().toString().trim();
                handleReason = et_handle_reason.getEditText().getText().toString().trim();
                handleWay = et_handle_way.getEditText().getText().toString().trim();
                handleResult = et_handle_result.getEditText().getText().toString().trim();
                if (!TextUtils.isEmpty(handlePerson) && !TextUtils.isEmpty(handleTime) && !TextUtils.isEmpty(handleReason) && !TextUtils.isEmpty(handleWay) && !TextUtils.isEmpty(handleResult)) {
                    //弹出对话框，确定提交
                    new MaterialDialog.Builder(YaLiJiDetailActivity.this)
                            .title("确认")
                            .content("请问您确定填写无误并提交吗？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //提交到服务器
                                    ToastUtils.showToast(YaLiJiDetailActivity.this, "提交");

                                    new MaterialDialog.Builder(YaLiJiDetailActivity.this)
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
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator(0, 0, 0, 0);
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
