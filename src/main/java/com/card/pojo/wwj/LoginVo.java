package com.card.pojo.wwj;

import java.io.Serializable;

public class LoginVo implements Serializable {

    private String deviceType;
    private String telInfo;
    private String platformType;
    private String version;
    private String deviceId;
    private String account;

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getTelInfo() {
        return telInfo;
    }

    public void setTelInfo(String telInfo) {
        this.telInfo = telInfo;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getPlatformType() {
        return platformType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

}
