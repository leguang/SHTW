package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by leguang on 2016/5/18 0018.
 */
public class LaboratoryFragmentRecyclerViewItemData {

    /**
     * data : [[{"realCount":"0","departName":"监理公司","testName":"砼抗压强度","testtype":"100014","syjCount":"4","realPer":"0.00","testCount":"8","notQualifiedCount":"0","sysCount":"2","userGroupId":"f89aff075317222c01531b866ec50010"},{"realCount":"0","departName":"监理公司","testName":"钢筋拉力","testtype":"100047","syjCount":"4","realPer":"0.00","testCount":"21","notQualifiedCount":"3","sysCount":"2","userGroupId":"f89aff075317222c01531b866ec50010"},{"realCount":"0","departName":"监理公司","testName":"钢筋机械连接接头","testtype":"100049","syjCount":"4","realPer":"0.00","testCount":"5","notQualifiedCount":"1","sysCount":"2","userGroupId":"f89aff075317222c01531b866ec50010"}]]
     * success : true
     */

    private boolean success;
    /**
     * realCount : 0
     * departName : 监理公司
     * testName : 砼抗压强度
     * testtype : 100014
     * syjCount : 4
     * realPer : 0.00
     * testCount : 8
     * notQualifiedCount : 0
     * sysCount : 2
     * userGroupId : f89aff075317222c01531b866ec50010
     */

    private List<List<DataBean>> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        private String realCount;
        private String departName;
        private String testName;
        private String testtype;
        private String syjCount;
        private String realPer;
        private String testCount;
        private String notQualifiedCount;
        private String sysCount;
        private String userGroupId;

        public String getRealCount() {
            return realCount;
        }

        public void setRealCount(String realCount) {
            this.realCount = realCount;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getTesttype() {
            return testtype;
        }

        public void setTesttype(String testtype) {
            this.testtype = testtype;
        }

        public String getSyjCount() {
            return syjCount;
        }

        public void setSyjCount(String syjCount) {
            this.syjCount = syjCount;
        }

        public String getRealPer() {
            return realPer;
        }

        public void setRealPer(String realPer) {
            this.realPer = realPer;
        }

        public String getTestCount() {
            return testCount;
        }

        public void setTestCount(String testCount) {
            this.testCount = testCount;
        }

        public String getNotQualifiedCount() {
            return notQualifiedCount;
        }

        public void setNotQualifiedCount(String notQualifiedCount) {
            this.notQualifiedCount = notQualifiedCount;
        }

        public String getSysCount() {
            return sysCount;
        }

        public void setSysCount(String sysCount) {
            this.sysCount = sysCount;
        }

        public String getUserGroupId() {
            return userGroupId;
        }

        public void setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
        }
    }
}
