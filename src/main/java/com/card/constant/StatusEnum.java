package com.card.constant;

/**
 * @author: yangPan
 * @date: 2019/10/27 15:00
 * @description:
 */
public enum StatusEnum {
    VALID("1","有效"),
    INVALID("0","无效");

    private String code;
    private String desc;

    StatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
