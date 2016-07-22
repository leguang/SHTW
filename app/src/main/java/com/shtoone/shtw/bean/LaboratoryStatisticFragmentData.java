package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by leguang on 2016/7/18 0018.
 */
public class LaboratoryStatisticFragmentData {

    /**
     * data : [{"qualifiedCount":"888","testType":"100014","notqualifiedCount":"3","testCount":"900","validCount":"9","userGroupId":"f89b12c254c7c0920154c7d2bce40013","qualifiedPer":"98.67"},{"qualifiedCount":"54","testType":"100047","notqualifiedCount":"3","testCount":"57","validCount":"0","userGroupId":"f89b12c254c7c0920154c7d2bce40013","qualifiedPer":"94.74"},{"qualifiedCount":"16","testType":"100048","notqualifiedCount":"0","testCount":"16","validCount":"0","userGroupId":"f89b12c254c7c0920154c7d2bce40013","qualifiedPer":"100.00"},{"qualifiedCount":"14","testType":"100049","notqualifiedCount":"1","testCount":"15","validCount":"0","userGroupId":"f89b12c254c7c0920154c7d2bce40013","qualifiedPer":"93.33"}]
     * success : true
     */

    private boolean success;
    /**
     * qualifiedCount : 888
     * testType : 100014
     * notqualifiedCount : 3
     * testCount : 900
     * validCount : 9
     * userGroupId : f89b12c254c7c0920154c7d2bce40013
     * qualifiedPer : 98.67
     */

    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String qualifiedCount;
        private String testType;
        private String notqualifiedCount;
        private String testCount;
        private String validCount;
        private String userGroupId;
        private String qualifiedPer;

        public String getQualifiedCount() {
            return qualifiedCount;
        }

        public void setQualifiedCount(String qualifiedCount) {
            this.qualifiedCount = qualifiedCount;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

        public String getNotqualifiedCount() {
            return notqualifiedCount;
        }

        public void setNotqualifiedCount(String notqualifiedCount) {
            this.notqualifiedCount = notqualifiedCount;
        }

        public String getTestCount() {
            return testCount;
        }

        public void setTestCount(String testCount) {
            this.testCount = testCount;
        }

        public String getValidCount() {
            return validCount;
        }

        public void setValidCount(String validCount) {
            this.validCount = validCount;
        }

        public String getUserGroupId() {
            return userGroupId;
        }

        public void setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
        }

        public String getQualifiedPer() {
            return qualifiedPer;
        }

        public void setQualifiedPer(String qualifiedPer) {
            this.qualifiedPer = qualifiedPer;
        }
    }
}
