package com.card.pojo.wwj;

import java.io.Serializable;

public class BuildInfo implements Serializable {
    private int SDK_INT;
    private String DEVICE;
    private String CPU_ABI;
    private String CPU_ABI2;
    private String BRAND;
    private String BOARD;
    private String FINGERPRINT;
    private String MANUFACTURER;
    private String MODEL;
    private String PRODUCT;
    private String ID;
    private String SERIAL;
    private String USER;
    private String RadioVersion;

    public int getSDK_INT() {
        return SDK_INT;
    }

    public void setSDK_INT(int SDK_INT) {
        this.SDK_INT = SDK_INT;
    }

    public String getDEVICE() {
        return DEVICE;
    }

    public void setDEVICE(String DEVICE) {
        this.DEVICE = DEVICE;
    }

    public String getCPU_ABI() {
        return CPU_ABI;
    }

    public void setCPU_ABI(String CPU_ABI) {
        this.CPU_ABI = CPU_ABI;
    }

    public String getCPU_ABI2() {
        return CPU_ABI2;
    }

    public void setCPU_ABI2(String CPU_ABI2) {
        this.CPU_ABI2 = CPU_ABI2;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getBOARD() {
        return BOARD;
    }

    public void setBOARD(String BOARD) {
        this.BOARD = BOARD;
    }

    public String getFINGERPRINT() {
        return FINGERPRINT;
    }

    public void setFINGERPRINT(String FINGERPRINT) {
        this.FINGERPRINT = FINGERPRINT;
    }

    public String getMANUFACTURER() {
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER) {
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getPRODUCT() {
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT) {
        this.PRODUCT = PRODUCT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getRadioVersion() {
        return RadioVersion;
    }

    public void setRadioVersion(String radioVersion) {
        RadioVersion = radioVersion;
    }
}
