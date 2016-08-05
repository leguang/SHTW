package com.shtoone.shtw.bean;

/**
 * Created by leguang on 2016/8/4 0004.
 */
public class DepartmentData {
    public String departmentID;
    public String departmentName;
    public int fromTo;

    //克隆
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
