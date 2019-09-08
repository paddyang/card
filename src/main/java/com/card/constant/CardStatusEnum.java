package com.card.constant;

/**
 * @author: yangPan
 * @date: 2019/7/9 23:11
 * @description:
 */
public enum CardStatusEnum {

    NOT_ACTIVE(0,"未激活"),
    ACTIVATED(1,"已激活"),
    LOCKED(2,"已锁定");

    private int code;
    private String desc;

    CardStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
