package com.shtoone.shtw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dd.CircularProgressButton;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by leguang on 2016/6/01 0031.
 */

public class DialogActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "DialogActivity";
    private TextInputLayout start_date_time;
    private TextInputLayout end_date_time;
    private CircularProgressButton bt_search;
    private MaterialSpinner select_equipment;
    private MaterialSpinner select_test_type;
    private ImageView iv_cancel;
    private boolean isStartDateTime;
    private String startDateTime;
    private String endDateTime;
    private ParametersData parametersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
        initData();
    }

    @Override
    protected int setContainerId() {
        return 0;
    }

    private void initView() {
        start_date_time = (TextInputLayout) findViewById(R.id.start_date_time_dialog);
        end_date_time = (TextInputLayout) findViewById(R.id.end_date_time_dialog);
        bt_search = (CircularProgressButton) findViewById(R.id.bt_search_dialog);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel_dialog);
        select_equipment = (MaterialSpinner) findViewById(R.id.select_equipment_dialog);
        select_test_type = (MaterialSpinner) findViewById(R.id.select_test_type_dialog);
    }

    private void initData() {
        parametersData = BaseApplication.parametersData;
        start_date_time.getEditText().setInputType(InputType.TYPE_NULL);

        //设置显示上一次查询条件的数据
        if (!parametersData.isFirst) {
            start_date_time.getEditText().setText(parametersData.startDateTime);
            end_date_time.getEditText().setText(parametersData.endDateTime);
            select_equipment.setSelection(3);
            select_test_type.setSelection(2);
        }

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

        String[] equipments = {"压力机1", "压力机2", "压力机3", "压力机4", "压力机5", "压力机6", "压力机7", "压力机8"};
        ArrayAdapter<String> equipmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, equipments);
        equipmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_equipment.setAdapter(equipmentsAdapter);

        String[] testTypes = {"钢筋拉力1", "钢筋拉力2", "钢筋拉力3", "钢筋拉力4", "钢筋拉力5", "钢筋拉力6", "钢筋拉力7", "钢筋拉力8"};
        ArrayAdapter<String> testTypessAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testTypes);
        testTypessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_test_type.setAdapter(testTypessAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel_dialog:
                ToastUtils.showToast(this, "取消");
                finish();
                break;

            case R.id.bt_search_dialog:
                ToastUtils.showToast(this, "查询");
                parametersData.isFirst = false;
                BaseApplication.bus.post(BaseApplication.parametersData);
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
            parametersData.startDateTime = startDateTime;
        } else {
            endDateTime = endDateTime + timeString;
            end_date_time.getEditText().setText(endDateTime);
            parametersData.endDateTime = endDateTime;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
