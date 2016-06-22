package com.shtoone.shtw.bean;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by leguang on 2016/5/31 0031.
 * 请求参数实体类
 */
public class ParametersData {
    private static final String TAG = "ParametersData";
    public String startDateTime = "2015-03-01 00:00:00";
    public String endDateTime = "2016-06-01 00:00:00";
    public String userGroupID = "";
    public String deviceType = "";
    public String testType = "";
    public String disposition = "";
    public String level = "";
    public String isQualified = "";
    public String equipmentID = "";
    public String currentPage = "";
    public String isReal = "";
    public boolean isFirst = true;
    public String detailID = "";
    public String fromTo = "";

    public ParametersData() {
        initParametersData();
    }

    private void initParametersData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar rld = Calendar.getInstance();
        endDateTime = sdf.format(rld.getTime());
        rld.add(Calendar.MONTH, -3);
        startDateTime = sdf.format(rld.getTime());
    }
}
