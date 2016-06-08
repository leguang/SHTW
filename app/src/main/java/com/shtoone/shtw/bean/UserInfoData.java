package com.shtoone.shtw.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 * Created by leguang on 2016/5/11 0031.
 */
public class UserInfoData implements Serializable {

    private String userPhoneNum;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    private String departName;
    private QuanxianEntity quanxian;
    private String xmmc;
    private String updateDepartTime;
    private String departId;
    private String userFullName;
    private String type;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    private String userRole;
    private boolean success;

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public void setQuanxian(QuanxianEntity quanxian) {
        this.quanxian = quanxian;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public void setUpdateDepartTime(String updateDepartTime) {
        this.updateDepartTime = updateDepartTime;
    }

    public List<SMSGroupEntity> getSMSGroup() {
        return SMSGroup;
    }

    public void setSMSGroup(List<SMSGroupEntity> SMSGroup) {
        this.SMSGroup = SMSGroup;
    }

    private List<SMSGroupEntity> SMSGroup;

    public static class SMSGroupEntity {
        private int id;
        private String phone;
        private String phonename;
        private String name;

        public void setId(int id) {
            this.id = id;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPhonename(String phonename) {
            this.phonename = phonename;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public void setType(String type) {
            this.type = type;
        }

        private int mid;

        public String getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getPhone() {
            return phone;
        }

        public String getPhonename() {
            return phonename;
        }

        public String getName() {
            return name;
        }

        public int getMid() {
            return mid;
        }

        private String type;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public QuanxianEntity getQuanxian() {
        return quanxian;
    }

    public String getXmmc() {
        return xmmc;
    }

    public String getUpdateDepartTime() {
        return updateDepartTime;
    }

    public String getDepartId() {
        return departId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getType() {
        return type;
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 用户权限
     */
    public static class QuanxianEntity implements Serializable {
        private boolean hntchaobiaoReal;
        private boolean hntchaobiaoSp;
        private boolean syschaobiaoReal;

        public boolean isSyschaobiaoReal() {
            return syschaobiaoReal;
        }

        public void setSyschaobiaoReal(boolean syschaobiaoReal) {
            this.syschaobiaoReal = syschaobiaoReal;
        }

        public void setHntchaobiaoReal(boolean hntchaobiaoReal) {
            this.hntchaobiaoReal = hntchaobiaoReal;
        }

        public void setHntchaobiaoSp(boolean hntchaobiaoSp) {
            this.hntchaobiaoSp = hntchaobiaoSp;
        }

        public boolean isHntchaobiaoReal() {
            return hntchaobiaoReal;
        }

        public boolean isHntchaobiaoSp() {
            return hntchaobiaoSp;
        }


        public QuanxianEntity() {
        }

        private QuanxianEntity(Parcel in) {
            this.hntchaobiaoReal = in.readByte() != 0;
            this.hntchaobiaoSp = in.readByte() != 0;
            this.syschaobiaoReal = in.readByte() != 0;
        }

        public static final Parcelable.Creator<QuanxianEntity> CREATOR = new Parcelable.Creator<QuanxianEntity>() {
            public QuanxianEntity createFromParcel(Parcel source) {
                return new QuanxianEntity(source);
            }

            public QuanxianEntity[] newArray(int size) {
                return new QuanxianEntity[size];
            }
        };
    }


    public UserInfoData() {
    }

    private UserInfoData(Parcel in) {
        this.userPhoneNum = in.readString();
        this.departName = in.readString();
        this.quanxian = in.readParcelable(QuanxianEntity.class.getClassLoader());
        this.xmmc = in.readString();
        this.updateDepartTime = in.readString();
        this.departId = in.readString();
        this.userRole = in.readString();
        this.userFullName = in.readString();
        this.type = in.readString();
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<UserInfoData> CREATOR = new Parcelable.Creator<UserInfoData>() {
        public UserInfoData createFromParcel(Parcel source) {
            return new UserInfoData(source);
        }

        public UserInfoData[] newArray(int size) {
            return new UserInfoData[size];
        }
    };
}
