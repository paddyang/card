package com.card.pojo.wwj;

import java.io.Serializable;

public class TelInfo implements Serializable {

    private String buildInfo;
    private String androidId;
    private String deviceId;
    private String imei;
    private String meid;
    private String userHandle;
    private int vuid;

    public String getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(String buildInfo) {
        this.buildInfo = buildInfo;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public int getVuid() {
        return vuid;
    }

    public void setVuid(int vuid) {
        this.vuid = vuid;
    }
}
