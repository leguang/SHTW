package com.shtoone.shtw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dd.CircularProgressButton;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by leguang on 2016/6/01 0031.
 */

public class DialogActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = DialogActivity.class.getSimpleName();
    private TextInputLayout start_date_time;
    private TextInputLayout end_date_time;
    private CircularProgressButton bt_search;
    private MaterialSpinner ms_select_equipment;
    private MaterialSpinner ms_select_test_type;
    private ImageView iv_cancel;
    private boolean isStartDateTime;
    private String startDateTime;
    private String endDateTime;
    private ParametersData mParametersData;
    private String[] equipmentNames = {"1标压力机", "3标压力机"};
    private String[] equipmentIDs = {"sphntyl0101", "sphntyl0301"};

    private String[] testTypeNames = {"砼抗压强度", "钢筋拉力", "钢筋焊接接头", "钢筋机械连接接头"};
    private String[] testTypeIDs = {"100014", "100047", "100048", "100049"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        initView();
        initData();
//        getDataFromNetwork();
    }

    private void initView() {
        //获取传过来的参数对象
        mParametersData = (ParametersData) getIntent().getSerializableExtra(ConstantsUtils.PARAMETERS);
        start_date_time = (TextInputLayout) findViewById(R.id.start_date_time_dialog);
        end_date_time = (TextInputLayout) findViewById(R.id.end_date_time_dialog);
        bt_search = (CircularProgressButton) findViewById(R.id.bt_search_dialog);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel_dialog);
        ms_select_equipment = (MaterialSpinner) findViewById(R.id.ms_select_equipment_dialog);
        ms_select_test_type = (MaterialSpinner) findViewById(R.id.ms_select_test_type_dialog);

        //设置哪些条件选择该显示，默认只有时间选择是显示的
        switch (mParametersData.fromTo) {
            case ConstantsUtils.LABORATORYFRAGMENT:
                //默认开始和结束时间是可见的
                break;
            case ConstantsUtils.YALIJIFRAGMENT:
                //设置设备和试验类型的下拉选择可见
                ms_select_equipment.setVisibility(View.VISIBLE);
                ms_select_test_type.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initData() {

        //根据传过来的参数对象来设置这些选择框该显示的内容
        start_date_time.getEditText().setText(mParametersData.startDateTime);
        end_date_time.getEditText().setText(mParametersData.endDateTime);

        start_date_time.getEditText().setInputType(InputType.TYPE_NULL);
        end_date_time.getEditText().setInputType(InputType.TYPE_NULL);
        iv_cancel.setOnClickListener(this);
        bt_search.setOnClickListener(this);

        start_date_time.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setStartDateTime();
                        break;
                }
                return true;
            }
        });

        end_date_time.getEditText().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setEndDateTime();
                        break;
                }
                return true;
            }
        });

        ArrayAdapter<String> equipmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, equipmentNames);
        equipmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ms_select_equipment.setAdapter(equipmentsAdapter);
        for (int i = 0; i < equipmentIDs.length; i++) {
            if (mParametersData.equipmentID.equals(equipmentIDs[i])) {
                ms_select_equipment.setSelection(i + 1);
                KLog.e("默认：" + (i + 1) + "个");
            }
        }

        ms_select_equipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                KLog.e("选择第：" + i + "个");
                if (i >= 0) {
                    mParametersData.equipmentID = equipmentIDs[i];
                    KLog.e("equipmentIDs[i]:" + equipmentNames[i]);
                } else if (i == -1) {
                    mParametersData.equipmentID = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> testTypessAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testTypeNames);
        testTypessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ms_select_test_type.setAdapter(testTypessAdapter);

        for (int i = 0; i < testTypeIDs.length; i++) {
            if (mParametersData.testTypeID.equals(testTypeIDs[i])) {
                ms_select_test_type.setSelection(i + 1);
            }
        }


        ms_select_test_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                KLog.e("选择第：" + i + "个");
                if (i >= 0) {
                    mParametersData.testTypeID = testTypeIDs[i];
                    KLog.e("testTypeIDs[i]:" + testTypeNames[i]);
                } else if (i == -1) {
                    mParametersData.testTypeID = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel_dialog:
                finish();
                break;

            case R.id.bt_search_dialog:
                BaseApplication.bus.post(mParametersData);
                finish();
                break;
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
        if (isStartDateTime) {
            startDateTime = startDateTime + timeString;
            start_date_time.getEditText().setText(startDateTime);
            mParametersData.startDateTime = startDateTime;
        } else {
            endDateTime = endDateTime + timeString;
            end_date_time.getEditText().setText(endDateTime);
            mParametersData.endDateTime = endDateTime;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String monthString = (++monthOfYear) < 10 ? "0" + (monthOfYear) : "" + (monthOfYear);
        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String dateString = year + "-" + monthString + "-" + dayString + " ";
        if (isStartDateTime) {
            startDateTime = dateString;
        } else {
            endDateTime = dateString;
        }
        showTimePicker();
    }

//    /********************
//     * 以下为临时作用
//     ***************************/
//
//    private void getDataFromNetwork() {
//        //联网获取数据
//        //还没有判断url，用户再判断
//        HttpUtils.getRequest(URL.getTestType("1"), new HttpUtils.HttpListener() {
//            @Override
//            public void onSuccess(String response) {
//                KLog.json(response);
//                parseData(response);
//            }
//
//            @Override
//            public void onFailed(VolleyError error) {
//                //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
//                if (!NetworkUtils.isConnected(DialogActivity.this)) {
//                    //提示网络异常,让用户点击设置网络
////                    pageStateLayout.showNetError();
//                } else {
//                    //服务器异常，展示错误页面，点击刷新
////                    pageStateLayout.showError();
//                }
//            }
//        });
//    }
//
//    private TestTypeData itemsData;
//
//    protected void parseData(String response) {
//        if (!TextUtils.isEmpty(response)) {
//            itemsData = new Gson().fromJson(response, TestTypeData.class);
//            if (null != itemsData) {
//                if (itemsData.isSuccess()) {
////                    pageStateLayout.showContent();
////                    setAdapter();
//                } else {
//                    //提示数据为空，展示空状态
////                    pageStateLayout.showEmpty();
//                }
//            } else {
//                //提示数据解析异常，展示错误页面
////                pageStateLayout.showError();
//            }
//        } else {
//            //提示返回数据异常，展示错误页面
////            pageStateLayout.showError();
//        }
//    }
}
